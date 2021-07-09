package com.tekinumut.eruyemekhane.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayoutMediator
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.base.BaseFragmentVB
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.databinding.FragmentHomeBinding
import com.tekinumut.eruyemekhane.ui.home.pager.HomePagerAdapter
import com.tekinumut.eruyemekhane.utils.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = HomePagerAdapter(this)
        mainActivity.supportActionBar?.hide()
        initAdView()
        setListeners()
        binding.viewPager.offscreenPageLimit = FoodListType.values().size
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(FoodListType.values()[position].nameRes)
        }.attach()
    }

    private fun initAdView() {
        val isRemoveBannerChecked = preferencesManager.isRemoveBannerChecked()
        if (isRemoveBannerChecked) {
            val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)
        }
        binding.adView.isVisible = isRemoveBannerChecked
    }

    private fun setListeners() {
        binding.ivSettings.setOnClickListener { showPopup(it) }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(v.context, v)
        popup.inflate(R.menu.menu_main)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_settings -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                    findNavController().navigate(action)
                }
                R.id.menu_about -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToAboutFragment()
                    findNavController().navigate(action)
                }
            }
            true
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.supportActionBar?.show()
    }
}
