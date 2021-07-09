package com.tekinumut.eruyemekhane.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tekinumut.eruyemekhane.ui.main.MainActivity

abstract class BaseDialogFragment : DialogFragment() {

    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}