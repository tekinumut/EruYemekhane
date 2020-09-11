package com.Mtkn.umutt.eruyemekhane.activities

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.library.Constants
import com.Mtkn.umutt.eruyemekhane.library.SafeClickListener
import com.Mtkn.umutt.eruyemekhane.library.SecondPref
import com.Mtkn.umutt.eruyemekhane.library.Utility
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.about_settings.*
import kotlinx.android.synthetic.main.about_settings.view.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (intent.action == getString(R.string.settings))
            supportFragmentManager.beginTransaction().replace(R.id.settings_container, Settings()).commit()
        if (intent.action == getString(R.string.about))
            supportFragmentManager.beginTransaction().replace(R.id.settings_container, About()).commit()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Ayarlar ekranını yönetir
     */
    class Settings : PreferenceFragmentCompat() {

        // private val firstTabList by lazy { findPreference<ListPreference>(getString(R.string.firstTabKey))!! }
        private val nightModeList by lazy { findPreference<ListPreference>(getString(R.string.nightModeKey))!! }
        private val adActivity by lazy { findPreference<SwitchPreference>(getString(R.string.adKey))!! }
        private lateinit var secondPref: SecondPref
        private var rewardAdInfoDialog: Dialog? = null
        private var currentToast: Toast? = null
        private lateinit var firebaseAnalytics: FirebaseAnalytics

        //  private val focusFullList by lazy { findPreference<SwitchPreference>(getString(R.string.focusNonEmptyKey))!! }
        private lateinit var rewardedAd: RewardedAd

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
            secondPref = SecondPref.getInstance(requireContext())

            if (Utility.isRewardAdTimeExpired(requireContext())) {
                createAndLoadRewardedAd()
            }

            nightModeList.setOnPreferenceChangeListener { _, newValue ->
                Utility.setTheme(newValue.toString().toInt())
                true
            }

            adActivity.summaryProvider = Preference.SummaryProvider<SwitchPreference> {
                if (it.isChecked) getString(R.string.adSummaryActive)
                else {
                    val expireTimeStamp = secondPref.getLong(getString(R.string.prefRewardExpireDate), Constants.defRewardExpireDate.time)
                    val dateOnly = Utility.timeStampToDateString(expireTimeStamp).run { substring(0, length - 9) }
                    getString(R.string.adSummaryPassive, dateOnly)
                }
            }

            adActivity.setOnPreferenceChangeListener { _, newValue ->
                val status: Boolean = newValue as Boolean
                // Eğer reklamları kapatmak istiyorsa
                if (!status) {
                    // Eğer reklamları kapatabilecek şart sağlanmıyorsa.
                    if (Utility.isRewardAdTimeExpired(requireContext())) {
                        loadAndInitDialog()
                    } else { // Sağlanıyorsa reklamları kapat.
                        adActivity.isChecked = false
                    }
                }
                // Reklamları şart olmadan açabilir :)
                else {
                    adActivity.isChecked = true
                }
                // Şart durumuna göre manuel olarak switch değişecek.
                false
            }

        }

        private fun loadAndInitDialog() {
            rewardAdInfoDialog = Dialog(requireActivity()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_disable_reward_ad)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btnAccept = findViewById<Button>(R.id.btnAccept)
                val btnReject = findViewById<Button>(R.id.btnReject)

                btnReject.setOnClickListener { dismiss() }
                btnAccept.setOnSafeClickListener { startRewardAd() }
                show()
            }
        }

        /**
         * Ödüllü reklamı init ediyoruz.
         */
        private fun createAndLoadRewardedAd() {
            rewardedAd = RewardedAd(requireContext(), getString(R.string.reward_ad_unit_id))
            val adloadCallBack = object : RewardedAdLoadCallback() {
                override fun onRewardedAdLoaded() {
                    super.onRewardedAdLoaded()
                    Log.e("loaded", "lodaded")
                }

                override fun onRewardedAdFailedToLoad(p0: LoadAdError) {
                    super.onRewardedAdFailedToLoad(p0)
                    Log.e("failed", "faill $p0")
                }
            }
            rewardedAd.loadAd(AdRequest.Builder().build(), adloadCallBack)
        }

        private fun startRewardAd() {
            if (rewardedAd.isLoaded) {
                // onRewardedAdClosed hep çalıştığı için kullanıyorum
                var isEarned = false
                val adCallback = object : RewardedAdCallback() {

                    override fun onRewardedAdOpened() {
                        // Ad opened.
                        isEarned = false
                    }

                    override fun onRewardedAdClosed() {
                        // Ad closed.
                        if (isEarned) {
                            secondPref.save(getString(R.string.prefRewardExpireDate), Constants.nextOneMonthTimeStamp())
                            secondPref = SecondPref.getInstance(requireContext())
                            adActivity.isChecked = false
                            showCurrentToast(getString(R.string.ad_rewarded), Toast.LENGTH_LONG)
                        } else {
                            showCurrentToast(getString(R.string.ad_closed), Toast.LENGTH_SHORT)
                        }
                    }

                    override fun onUserEarnedReward(p0: RewardItem) {
                        isEarned = true
                        val bundle = Bundle()
                        bundle.putString("is_reward_watched", "reklam izlendi")
                        firebaseAnalytics.logEvent("reward_earned_count", bundle)
                    }

                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        val errorCause = when (errorCode) {
                            ERROR_CODE_INTERNAL_ERROR -> "Teknik bir hata meydana geldi. Lütfen tekrar deneyiniz."
                            ERROR_CODE_AD_REUSED -> "Reklam zaten bir kere gösterildi."
                            ERROR_CODE_NOT_READY -> getString(R.string.ad_failed_load)
                            ERROR_CODE_APP_NOT_FOREGROUND -> "Reklam uygulama ön planda değilken oynatılamaz."
                            else -> "Teknik bir hata meydana geldi. Lütfen tekrar deneyiniz."
                        }
                        showCurrentToast(errorCause, Toast.LENGTH_LONG)
                    }
                }
                rewardedAd.show(requireActivity(), adCallback)
            } else {
                createAndLoadRewardedAd()
                showCurrentToast(getString(R.string.ad_failed_load), Toast.LENGTH_LONG)
            }
        }

        private fun showCurrentToast(text: String, length: Int) {
            currentToast?.cancel()
            currentToast = Toast.makeText(requireActivity(), text, length)
            currentToast?.show()
        }

        private fun dismissAdInfoDialog() {
            rewardAdInfoDialog?.dismiss()
        }

        private fun dismissCurrentToast() {
            currentToast?.cancel()
        }

        /**
         * Alınan butonu belirtilen saniye boyunca disable eder.
         */
        private fun View.setOnSafeClickListener(onSafeClick: (View) -> Unit) {
            val safeClickListener = SafeClickListener { onSafeClick(it) }
            setOnClickListener(safeClickListener)
        }

        override fun onPause() {
            super.onPause()
            dismissCurrentToast()
            dismissAdInfoDialog()
        }

    }

    /**
     * Hakkında ekranını yönetir
     */
    class About : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val rootView: View = inflater.inflate(R.layout.about_settings, container, false)

            //Textview'ı genişletme ve daraltma işlemlerinin yapıldığı metot
            rootView.click_to_expand.setOnClickListener { onGuidePresentationChange(rootView) }

            rootView.tv_user_guide.setOnClickListener { onGuidePresentationChange(rootView) }

            //Geri bildirim gönder. ( Mail yolu ile )
            rootView.send_feedback.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.myGmail)))
                startActivity(Intent.createChooser(intent, getString(R.string.send_mail)))
            }

            //Uygulamayı değerlendirme sayfasına git
            rootView.rate_app.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context?.packageName)))
                } catch (e: ActivityNotFoundException) {
                    val backUpURL = "https://play.google.com/store/apps/details?id=" + context?.packageName
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(backUpURL)))
                }
            }

            //Github'da kodları görüntüle
            rootView.github_source_code.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_address))))
            }

            return rootView
        }

        // Kullanım kılavuzu metninin görünüm durumunu düzenler
        private fun onGuidePresentationChange(view: View) {
            if (view.tv_user_guide.maxLines == 5) {
                onLess()
            } else if (view.tv_user_guide.maxLines == Integer.MAX_VALUE) {
                onExpand()
            }
        }

        //Eğer Genişletme butonuna tıkladıysam
        private fun onLess() {
            click_to_expand.text = getString(R.string.expand_less)
            click_to_expand.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_expand_less, 0, 0, 0)
            tv_user_guide.maxLines = Integer.MAX_VALUE
        }

        //Eğer Daraltma butonuna tıkladıysam
        private fun onExpand() {
            click_to_expand.text = getString(R.string.expand_more)
            click_to_expand.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_expand_more, 0, 0, 0)
            tv_user_guide.maxLines = 5
        }
    }

    /**
     * Üst bardaki geri butonunu kontrol eder
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> backToMainActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Geri tuşunu kontrol eder.
     * backToMainActivity intent verisinin aktarılması için super.OnBackPressed'den önce yazılmalı
     */
    override fun onBackPressed() {
        backToMainActivity()
        super.onBackPressed()
    }

    /**
     * Eğer seçili sayfa Ayarlar ise geri butonuna basıldığında ana sayfaya yönlendirir.
     * Hakkında kısmında buna gerek yok. Çünkü hakkında bölümü açılırken Mainactivity kapatılmıyor.
     */
    private fun backToMainActivity() {
        val resultIntent = Intent()
        resultIntent.putExtra(Constants.settingsPageKey, intent.action)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
