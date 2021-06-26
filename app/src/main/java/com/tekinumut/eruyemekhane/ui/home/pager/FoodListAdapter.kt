package com.tekinumut.eruyemekhane.ui.home.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients
import com.tekinumut.eruyemekhane.databinding.ItemFoodlistBinding
import com.tekinumut.eruyemekhane.ui.home.pager.FoodListAdapter.FoodListViewHolder

class FoodListAdapter : ListAdapter<FoodWithIngredients, FoodListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodlistBinding.inflate(layoutInflater, parent, false)
        return FoodListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FoodListViewHolder(
        private val binding: ItemFoodlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodWithIngredients: FoodWithIngredients) {
            binding.date.text = foodWithIngredients.food.date
            binding.totalCalorie.text = foodWithIngredients.food.totalCalorie
            binding.foodIngredients.text = "YEmek\nYemek2\nYemek3"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodWithIngredients>() {
            override fun areItemsTheSame(
                oldItem: FoodWithIngredients,
                newItem: FoodWithIngredients
            ): Boolean {
                return oldItem.food.id == newItem.food.id
            }

            override fun areContentsTheSame(
                oldItem: FoodWithIngredients,
                newItem: FoodWithIngredients
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}