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
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.Mtkn.umutt.eruyemekhane.R
import kotlinx.android.synthetic.main.about_settings.*
import kotlinx.android.synthetic.main.about_settings.view.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if (intent.action == "Ayarlar")
            supportFragmentManager.beginTransaction().replace(R.id.settings_container, Settings()).commit()
        if (intent.action == "Hakkinda")
            supportFragmentManager.beginTransaction().replace(R.id.settings_container, About()).commit()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class Settings : PreferenceFragmentCompat() {

        private lateinit var defaultTab: ListPreference
        private lateinit var nightMode: SwitchPreferenceCompat

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            defaultTab = findPreference("defaultTab")!!
            nightMode = findPreference("nightMode")!!

            nightMode.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> {
                if (it.isChecked)
                    getString(R.string.night_mode_active)
                else
                    getString(R.string.night_mode_passive)
            }

            nightMode.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }

        }
    }

    class About : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val rootView: View = inflater.inflate(R.layout.about_settings, container, false)

            //Textview'ı genişletme ve daraltma işlemlerinin yapıldığı metot
            rootView.click_to_expand.setOnClickListener {

                if (rootView.kullanim_kilavuzu_text.maxLines == 5) {
                    onLess()
                } else if (kullanim_kilavuzu_text.maxLines == Integer.MAX_VALUE) {
                    onExpand()
                }
            }

            //Geri bildirim gönder. ( Mail yolu ile )
            rootView.send_feedback.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.myGmail)))
                startActivity(Intent.createChooser(intent, "Mail gönder"))
            }

            //Uygulamayı değerlendirme sayfasına git
            rootView.rate_app.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context?.packageName)))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + context?.packageName)))
                }

            }

            //Github'da kodları görüntüle
            rootView.github_source_code.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_address))))
            }

            return rootView
        }

        //Eğer Genişletme butonuna tıkladıysam
        private fun onLess() {
            click_to_expand.text = getString(R.string.expand_less)
            click_to_expand.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_expand_less, 0, 0, 0)
            kullanim_kilavuzu_text.maxLines = Integer.MAX_VALUE
        }

        //Eğer Daraltma butonuna tıkladıysam
        private fun onExpand() {
            click_to_expand.text = getString(R.string.expand_more)
            click_to_expand.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_expand_more, 0, 0, 0)
            kullanim_kilavuzu_text.maxLines = 5
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                if (intent.action == "Ayarlar")
                    startActivity(Intent(this, MainActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //  startActivity(Intent(this, MainActivity::class.java))
        finish()
        if (intent.action == "Ayarlar")
            startActivity(Intent(this, MainActivity::class.java))
    }
}
