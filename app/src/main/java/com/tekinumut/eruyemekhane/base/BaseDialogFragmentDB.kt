package com.tekinumut.eruyemekhane.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialogFragmentDB<DB : ViewDataBinding>(
    private val resourceId: Int
) : BaseDialogFragment() {
    lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, resourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}