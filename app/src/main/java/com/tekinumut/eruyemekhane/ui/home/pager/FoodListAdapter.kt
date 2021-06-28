package com.tekinumut.eruyemekhane.ui.home.pager

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
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
            binding.totalCalories.text = foodWithIngredients.food.totalCalories
            val foodListText = foodIngredientsStyle(foodWithIngredients.foodIngredients)
            binding.foodIngredients.text = foodListText
        }

        private fun foodIngredientsStyle(ingredientsList: List<FoodIngredients>): SpannableStringBuilder {
            val spannableStringBuilder = SpannableStringBuilder()
            ingredientsList.forEach {
                spannableStringBuilder
                    .append("\u2022 ")
                    .append(it.name)
                    .append(" ")
                    .append(it.calorie?.calorieStyle() ?: "")
                    .append("\n")
            }
            spannableStringBuilder.run { delete(length - 1, length) }
            return spannableStringBuilder
        }

        private fun String.calorieStyle(): Spannable {
            val text = SpannableString(this)
            val calorieSize = binding.root.context.resources
                .getDimension(R.dimen.foodList_calorie_text_size).toInt()
            text.setSpan(TypefaceSpan("sans-serif"), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            text.setSpan(AbsoluteSizeSpan(calorieSize), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return text
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