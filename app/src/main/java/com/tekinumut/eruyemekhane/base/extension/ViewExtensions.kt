@file:Suppress("unused")

package com.tekinumut.eruyemekhane.base.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

private var toast: Toast? = null

/**
 * We block toast to stack.
 */
fun showToast(context: Context, @StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(context, context.getString(message), duration)
    toast?.show()
}

fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(context, message, duration)
    toast?.show()
}