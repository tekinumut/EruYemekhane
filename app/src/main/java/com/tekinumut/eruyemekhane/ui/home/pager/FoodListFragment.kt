package com.tekinumut.eruyemekhane.ui.home.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tekinumut.eruyemekhane.base.BaseFragment
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.databinding.FragmentFoodlistBinding
import com.tekinumut.eruyemekhane.utils.Resource
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : BaseFragment<FragmentFoodlistBinding>(FragmentFoodlistBinding::inflate) {

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
        binding.swipeRefreshFoodList.setOnRefreshListener(refreshListener)
        setupObservers()
        binding.incErrorFoodlist.btnOpenWebPage.setOnClickListener {
            Utility.openWebSiteWithCustomTabs(requireContext(), foodListType.webSiteUrl)
        }
    }

    private fun setupObservers() {
        viewModel.foodList.observe(viewLifecycleOwner, { response ->
            binding.progressBar.isVisible = response is Resource.Loading
            binding.incErrorFoodlist.root.isVisible = response is Resource.Error
            if (response !is Resource.Loading) {
                binding.swipeRefreshFoodList.isRefreshing = false
            }
            if (response is Resource.Success) {
                binding.recyclerFoodList.isVisible = response.data.isNotEmpty()
                if (response.data.isNotEmpty()) {
                    foodListAdapter.submitList(response.data)
                } else {
                    binding.incErrorFoodlist.root.isVisible = true
                }
            }
        })
        viewModel.isFragmentCreatedBefore.observe(viewLifecycleOwner, { createdBefore ->
            if (!createdBefore) {
                viewModel.fetchFoodList(foodListType)
                viewModel.setFragmentCreatedBefore(true)
            }
        })
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshFoodList.post {
            viewModel.fetchFoodList(foodListType)
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