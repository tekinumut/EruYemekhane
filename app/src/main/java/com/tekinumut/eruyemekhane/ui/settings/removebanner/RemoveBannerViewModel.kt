package com.tekinumut.eruyemekhane.ui.settings.removebanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.rewarded.RewardedAd
import com.tekinumut.eruyemekhane.base.BaseViewModel
import com.tekinumut.eruyemekhane.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveBannerViewModel @Inject constructor() : BaseViewModel() {

    private val _adStatus = MutableLiveData<Resource<RewardedAd>>(Resource.Loading)
    val adStatus: LiveData<Resource<RewardedAd>> = _adStatus

    fun setAdStatus(adStatus: Resource<RewardedAd>) {
        _adStatus.value = adStatus
    }
}