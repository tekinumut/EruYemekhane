package com.tekinumut.eruyemekhane.ui.home.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tekinumut.eruyemekhane.BuildConfig
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.base.BaseFragmentVB
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.databinding.FragmentFoodlistBinding
import com.tekinumut.eruyemekhane.utils.PreferencesManager
import com.tekinumut.eruyemekhane.utils.Resource
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodListFragment : BaseFragmentVB<FragmentFoodlistBinding>(FragmentFoodlistBinding::inflate) {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private lateinit var foodListType: FoodListType

    private val viewModel by viewModels<FoodListViewModel>()

    private val foodListAdapter = FoodListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListType = arguments?.get(ARG_FOOD_TYPE) as FoodListType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerFoodList.adapter = foodListAdapter
        binding.swipeRefreshFoodList.run {
            setOnRefreshListener(refreshListener)
            setColorSchemeResources(R.color.swipe_refresh_progress)
            setProgressBackgroundColorSchemeResource(R.color.swipe_refresh_bg)
        }
        initObservers()

        if (!viewModel.isCreatedBefore) {
            viewModel.fetchFoodList(foodListType, preferencesManager.updateListOnLaunch())
            viewModel.isCreatedBefore = true
        }

        binding.incErrorFoodlist.btnOpenWebPage.setOnClickListener {
            val webSiteUrl = BuildConfig.BASE_URL + foodListType.apiUrl
            Utility.openWebSiteWithCustomTabs(requireContext(), webSiteUrl)
        }
    }

    private fun initObservers() {
        viewModel.foodList.observe(viewLifecycleOwner, { response ->
            binding.progressBar.isVisible =
                response is Resource.Loading && !binding.swipeRefreshFoodList.isRefreshing
            binding.incErrorFoodlist.root.isVisible = response is Resource.Error
            if (response is Resource.Success) {
                binding.recyclerFoodList.isGone = response.data.isEmpty()
                if (response.data.isNotEmpty()) {
                    foodListAdapter.submitList(response.data)
                } else {
                    binding.incErrorFoodlist.root.isVisible = true
                }
            }
            if (response !is Resource.Loading) {
                binding.swipeRefreshFoodList.isRefreshing = false
            }
        })
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshFoodList.post {
            viewModel.fetchFoodList(foodListType, true)
        }
    }

    companion object {
        private const val ARG_FOOD_TYPE = "food_type"

        fun newInstance(foodListType: FoodListType): FoodListFragment {
            return FoodListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_FOOD_TYPE, foodListType)
                }
            }
        }
    }
}