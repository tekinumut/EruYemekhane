package com.tekinumut.eruyemekhane.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.base.BaseFragmentVB
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.databinding.FragmentHomeBinding
import com.tekinumut.eruyemekhane.ui.home.pager.HomePagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var pagerAdapter: HomePagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = HomePagerAdapter(this)
        binding.ivSettings.setOnClickListener { showPopup(it) }
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (FoodListType.values()[position]) {
                FoodListType.STUDENT -> getString(R.string.student)
                FoodListType.PERSONAL -> getString(R.string.personal)
            }
        }.attach()
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
}
