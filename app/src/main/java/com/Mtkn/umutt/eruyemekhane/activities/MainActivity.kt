package com.Mtkn.umutt.eruyemekhane.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.Mtkn.umutt.eruyemekhane.MainViewModel
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.TabOgrPer
import com.Mtkn.umutt.eruyemekhane.YemekModel
import com.Mtkn.umutt.eruyemekhane.library.Constants
import com.Mtkn.umutt.eruyemekhane.library.Resource
import com.Mtkn.umutt.eruyemekhane.library.Utility
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import javax.net.ssl.SSLHandshakeException

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val openSettingRequestCode = 123

    private val loadingDialog by lazy { Utility.getLoadingDialog(this) }
    private val mainViewModel: MainViewModel by viewModels()
    private val mainPref by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }
    private val typeOrder by lazy { mainPref.getString(getString(R.string.firstTabKey), "${Constants.ogrType}")?.toInt()!! }
    private val adActivity by lazy { mainPref.getBoolean(getString(R.string.adKey), true) }
    private val focusFullList by lazy { mainPref.getBoolean(getString(R.string.focusNonEmptyKey), true) }
    private val autoUpdateOnBegin by lazy { mainPref.getBoolean(getString(R.string.autoUpdateBeginKey), true) }
    private val view by lazy { findViewById<View>(android.R.id.content) }
    private var selectedTab = Constants.ogrType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBannerAd()
        refreshLayout.setOnRefreshListener(this)
        viewpager.offscreenPageLimit = 1
        viewpager.adapter = SectionPagerAdapter(this, typeOrder)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedTab = position
                refreshLayout.isRefreshing = false
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                toggleRefresh(state == ViewPager2.SCROLL_STATE_IDLE)
            }
        })
        loadAndInitData()
        initSettingsMenu()

        TabLayoutMediator(tab_layout, viewpager) { tab, position ->
            tab.text = getTabText(position)
        }.attach()
    }

    /**
     * Verileri yükler
     */
    private fun loadAndInitData() {
        if (autoUpdateOnBegin) {
            getFoodData(Constants.bothType)
        } else {
            mainViewModel.getAllFoods().observe(this, Observer {
                selectNonEmptyTab(it)
                Toast.makeText(this@MainActivity, getString(R.string.data_loaded_last_known), Toast.LENGTH_SHORT).show()
            })
        }
    }

    /**
     * Banner reklamı aktif et
     */
    private fun loadBannerAd() {
        val isTimeExpired = Utility.isRewardAdTimeExpired(applicationContext)
        if (isTimeExpired) {
            // Eğer reklam pasifleştirme süresi dolduysa, reklamları tekrar aktif et.
            mainPref.edit().putBoolean(getString(R.string.adKey), true).apply()
        }
        if (adActivity || isTimeExpired) {
            MobileAds.initialize(this) {}
            val adRequest = AdRequest.Builder().build()
            adViewBanner.loadAd(adRequest)
        } else {
            adViewBanner.visibility = View.GONE
        }
    }

    /**
     * Web sitesinden verileri çeker.
     */
    private fun getFoodData(type: Int) {
        if (Utility.isInternetAvailable(this)) {
            mainViewModel.getFoodData(type).observe(this, Observer { result ->
                when (result) {
                    Resource.InProgress -> loadingDialog.show()
                    is Resource.Success -> {
                        Toast.makeText(this, getString(R.string.data_updated), Toast.LENGTH_SHORT).show()
                        // Eğer veri ilk kez yükleniyorsa listenin dolu olduğu sayfayı aç
                        if (type == Constants.bothType) {
                            selectNonEmptyTab(result.data)
                        }
                        loadingDialog.dismiss()
                        refreshLayout.isRefreshing = false
                    }
                    is Resource.Error -> {
                        var message = getString(R.string.error_loading_data)
                        if (result.exception is SSLHandshakeException) {
                            message = getString(R.string.error_loading_data_certificate)
                        }
                        loadingDialog.dismiss()
                        refreshLayout.isRefreshing = false
                        showErrorSnackBar(message)
                    }
                }
            })
        } else {
            refreshLayout.isRefreshing = false
            Snackbar.make(view, getString(R.string.no_connection), 3000).show()
        }
    }

    /**
     * Eğer listenin biri boş diğeri dolu ise
     * otomatik olarak dolu listenin bulunduğu sayfayı açar
     */
    private fun selectNonEmptyTab(list: List<YemekModel>) {
        if (focusFullList) {
            var ogrCount = 0
            var perCount = 0
            list.forEach {
                if (it.type == Constants.ogrType) ogrCount += 1
                else if (it.type == Constants.perType) perCount += 1
            }
            // Eğer sadece Öğrenci listesi doluysa
            if (ogrCount > 0 && perCount == 0) {
                viewpager.currentItem = if (typeOrder == 0) Constants.ogrType else Constants.perType
            }
            // Eğer sadece Personel listesi doluysa
            if (perCount > 0 && ogrCount == 0) {
                viewpager.currentItem = if (typeOrder == 0) Constants.perType else Constants.ogrType
            }
        }
    }

    /**
     * Tab'ların text verisini belirler
     */
    private fun getTabText(position: Int): String {
        return when (position) {
            0 -> {
                if (typeOrder == 0) getString(R.string.ogrenci_title)
                else getString(R.string.personel_title)
            }
            1 -> {
                if (typeOrder == 0) getString(R.string.personel_title)
                else getString(R.string.ogrenci_title)
            }
            else -> getString(R.string.error)
        }
    }

    /**
     * Veriler yüklenirken bir hata meydana geldiğinde çağrılır.
     */
    private fun showErrorSnackBar(message: String) {
        Snackbar.make(view, message, 5000)
            .setAction(getString(R.string.open_web_site)) { Utility.openListWebSite(this) }
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }

    /**
     * Sağ üst köşede bulunan pop menüsünün işlevlerini tanımlar.
     */
    private fun initSettingsMenu() {
        ib_settings.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.settings_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_ayarlar -> openSettingsActivity(getString(R.string.settings))
                    R.id.menu_hakkinda -> openSettingsActivity(getString(R.string.about))
                }
                true
            }
            popup.show() //showing popup menu
        }
    }

    /**
     * Ayarlar sayfasını açar
     */
    private fun openSettingsActivity(action: String) {
        startActivityForResult(Intent(this, SettingsActivity::class.java).setAction(action), openSettingRequestCode)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /**
     * Bu metot ViewPager2 SwipeRefreshLayout içerisinde olduğundan dolayı
     * tablar arasında geçiş yaparken refreshlayout ile çakışmasını engeller.
     */
    private fun toggleRefresh(enabled: Boolean) {
        refreshLayout?.let { refreshLayout.isEnabled = enabled }
    }

    // An equivalent ViewPager2 adapter class
    class SectionPagerAdapter(fa: FragmentActivity, private val typeOrder: Int) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        // position 0-1 olarak gelecek
        // eğer ilk personel göster deniyorsa 1-0 olarak veri gönderilecek.
        override fun createFragment(position: Int): Fragment {
            return if (typeOrder == 0) {
                TabOgrPer.newInstance(position)
            } else {
                if (position == 0) TabOgrPer.newInstance(1)
                else TabOgrPer.newInstance(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adViewBanner.destroy()
        loadingDialog.dismiss()
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        refreshLayout.post { getFoodData(selectedTab) }
    }

    /**
     * Ayarlar sayfasından dönüşte hangi sayfadan gelindiğini takip eder.
     * Ayrıca Ayarlar sayfasının kapatıldığını anlarız.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == openSettingRequestCode) {
            val pageTitle = data?.extras?.getString(Constants.settingsPageKey)

            if (pageTitle.equals(getString(R.string.settings))) {
                viewpager.adapter = null
                recreate()
            }
        }
    }

}
