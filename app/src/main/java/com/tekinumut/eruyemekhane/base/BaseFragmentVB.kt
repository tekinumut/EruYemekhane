package com.tekinumut.eruyemekhane.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragmentVB<VB : ViewBinding>(private val inflate: Inflate<VB>) : BaseFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    open fun onDestroyedView() {}

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyedView()
        _binding = null
    }
}