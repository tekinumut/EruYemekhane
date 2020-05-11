package com.Mtkn.umutt.eruyemekhane.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.library.Utility
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

        private val nightModeList by lazy { findPreference<ListPreference>(getString(R.string.nightModeKey))!! }
        private val adActivity by lazy { findPreference<SwitchPreference>(getString(R.string.adKey))!! }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            nightModeList.setOnPreferenceChangeListener { _, newValue ->
                Utility.setTheme(newValue.toString().toInt())
                true
            }
            adActivity.summaryProvider = Preference.SummaryProvider<SwitchPreference> {
                if (it.isChecked) getString(R.string.adSummaryActive)
                else getString(R.string.adSummaryPassive)
            }
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
            android.R.id.home -> {
                backToMainActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Navigation bar'daki geri butonunu kontrol eder
     */
    override fun onBackPressed() {
        super.onBackPressed()
        backToMainActivity()
    }

    /**
     * Eğer seçili sayfa Ayarlar ise geri butonuna basıldığında ana sayfaya yönlendirir.
     * Hakkında kısmında buna gerek yok. Çünkü hakkında bölümü açılırken Mainactivity kapatılmıyor.
     */
    private fun backToMainActivity() {
        finish()
        if (intent.action == getString(R.string.settings)) {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }
}
