package com.tekinumut.eruyemekhane.ui.home.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tekinumut.eruyemekhane.data.enums.FoodListType

class HomePagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = FoodListType.values().size

    override fun createFragment(position: Int): Fragment {
        return FoodListFragment.newInstance(FoodListType.values()[position])
    }
}