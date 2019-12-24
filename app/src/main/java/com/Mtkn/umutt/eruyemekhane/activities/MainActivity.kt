package com.Mtkn.umutt.eruyemekhane.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.fragments.TabOgrenciPersonel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainPref: SharedPreferences
    private lateinit var adapter: SectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        MobileAds.initialize(this) {}
        val mAdView = findViewById<AdView>(R.id.adViewBanner)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //   showBuilder()

        adapter = SectionPagerAdapter(supportFragmentManager, tabLayout)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        val selectedTab = Integer.parseInt(mainPref.getString("defaultTab", "0")!!)

        if (selectedTab == 0) {
            tabLayout.getTabAt(0)?.text = getString(R.string.ogrenci_title)
            tabLayout.getTabAt(1)?.text = getString(R.string.personel_title)
        } else {
            tabLayout.getTabAt(0)?.text = getString(R.string.personel_title)
            tabLayout.getTabAt(1)?.text = getString(R.string.ogrenci_title)
        }

        openSettings.setOnClickListener {
            val popup = PopupMenu(this@MainActivity, it)
            popup.menuInflater.inflate(R.menu.settings_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_ayarlar -> {
                        startActivity(Intent(this, SettingsActivity::class.java).setAction("Ayarlar"))
                        finish()
                    }
                    R.id.menu_hakkinda -> {
                        startActivity(Intent(this, SettingsActivity::class.java).setAction("Hakkinda"))
                    }
                }
                true
            }
            popup.show() //showing popup menu
        }
    }

    class SectionPagerAdapter(fm: FragmentManager, private val tabLayout: TabLayout) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {

            val tabType: ArrayList<Int> = ArrayList()
            for (i in 0 until tabLayout.tabCount) {
                when (tabLayout.getTabAt(i)?.text.toString()) {
                    tabLayout.context.getString(R.string.ogrenci_title) -> tabType.add(0)
                    tabLayout.context.getString(R.string.personel_title) -> tabType.add(1)
                }
            }
            return when (position) {
                in 0..1 -> TabOgrenciPersonel.newInstance(tabType[position], position)
                else -> TabOgrenciPersonel.newInstance(0, 0)
            }
        }

        override fun getCount(): Int = 2
    }

}


