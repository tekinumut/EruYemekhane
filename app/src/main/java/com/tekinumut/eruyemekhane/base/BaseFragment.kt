package com.tekinumut.eruyemekhane.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tekinumut.eruyemekhane.ui.main.MainActivity

abstract class BaseFragment : Fragment() {

    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }
}