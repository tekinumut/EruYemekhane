package com.tekinumut.eruyemekhane.ui.home.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.databinding.FragmentFoodlistBinding
import com.tekinumut.eruyemekhane.utils.Resource
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment(R.layout.fragment_foodlist) {

    private lateinit var foodListType: FoodListType

    private var _binding: FragmentFoodlistBinding? = null
    private val binding: FragmentFoodlistBinding get() = _binding!!

    private val viewModel by viewModels<FoodListViewModel>()

    private val foodlistAdapter = FoodListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListType = arguments?.get(ARG_FOOD_TYPE) as FoodListType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodlistBinding.bind(view)
        binding.recyclerFoodList.adapter = foodlistAdapter
        setupObservers()
        binding.incErrorFoodlist.btnOpenWebPage.setOnClickListener {
            Utility.openWebSiteWithCustomTabs(requireContext(), foodListType.webSiteUrl)
        }
    }

    private fun setupObservers() {
        viewModel.getFoodList(foodListType).observe(viewLifecycleOwner, { response ->
            binding.progressBar.isVisible = response is Resource.Loading
            binding.incErrorFoodlist.root.isVisible = response is Resource.Error
            if (response is Resource.Success) {
                if (response.data.isNotEmpty()) {
                    foodlistAdapter.submitList(response.data)
                } else {
                    binding.incErrorFoodlist.root.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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