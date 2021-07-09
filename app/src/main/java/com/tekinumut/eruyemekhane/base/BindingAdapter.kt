package com.tekinumut.eruyemekhane.base

import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("setTextGone")
fun TextView.setTextGone(textValue: String?) {
    isGone = textValue.isNullOrEmpty()
    text = textValue
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isGone")
fun View.isGone(isGone: Boolean) {
    this.isGone = isGone
}

@BindingAdapter("isInvisible")
fun View.isInvisible(isInvisible: Boolean) {
    this.isInvisible = isInvisible
}