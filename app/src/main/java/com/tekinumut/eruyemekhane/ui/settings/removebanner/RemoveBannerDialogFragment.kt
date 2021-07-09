package com.tekinumut.eruyemekhane.ui.settings.removebanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.base.BaseDialogFragmentDB
import com.tekinumut.eruyemekhane.base.extension.showToast
import com.tekinumut.eruyemekhane.databinding.DialogRemoveBannerBinding
import com.tekinumut.eruyemekhane.utils.DataStoreManager
import com.tekinumut.eruyemekhane.utils.DateUtils
import com.tekinumut.eruyemekhane.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RemoveBannerDialogFragment : BaseDialogFragmentDB<DialogRemoveBannerBinding>(
    R.layout.dialog_remove_banner
) {
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val viewModel by viewModels<RemoveBannerViewModel>()

    private var mRewardedAd: RewardedAd? = null

    // is user watched reward ad successfully
    private var isEarned = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        isCancelable = false
        loadRewardedAd()
        initObservers()
        setListeners()
    }

    private fun initObservers() {
        viewModel.adStatus.observe(viewLifecycleOwner, { adStatus ->
            if (adStatus is Resource.Error) {
                binding.tvError.text = adStatus.errorMessage
            }
        })
    }

    private fun setListeners() {
        binding.btnReject.setOnClickListener { dialog?.dismiss() }
        binding.btnAccept.setOnClickListener { showRewardedAd() }
        binding.btnTryAgain.setOnClickListener { loadRewardedAd() }
    }

    private fun loadRewardedAd() {
        if (mRewardedAd == null) {
            viewModel.setAdStatus(Resource.Loading)
            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(
                requireContext(),
                getString(R.string.adUnitIdRemoveBanner),
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        onAdError(getString(R.string.ad_failed_load))
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        mRewardedAd = rewardedAd
                        viewModel.setAdStatus(Resource.Success(rewardedAd))
                    }
                }
            )
        }
    }

    private fun showRewardedAd() {
        mRewardedAd?.let {
            it.fullScreenContentCallback = fullScreenContentCallback
            it.show(mainActivity) {
                isEarned = true
                lifecycleScope.launch {
                    dataStoreManager.setRewardAdExpireTime(DateUtils.getNextMonthTimeStamp())
                    setFragmentResult(REQUEST_KEY, bundleOf(ON_REWARD_EARNED to true))
                }
            }
        } ?: kotlin.run {
            onAdError(getString(R.string.ad_not_ready_yet))
        }
    }

    private fun onAdError(message: String) {
        mRewardedAd = null
        viewModel.setAdStatus(Resource.Error(message))
    }

    private val fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdShowedFullScreenContent() = Unit

        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            // Called when ad fails to show.
            onAdError(getString(R.string.ad_failed_load))
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed before watch it.
            if (!isEarned) {
                onAdError(getString(R.string.ad_closed))
            } else {
                //  Called when ad is dismissed after successfully watched.
                dismiss()
                showToast(requireContext(), getString(R.string.ad_reward_earned), Toast.LENGTH_LONG)
            }

        }
    }
    companion object {
        const val ON_REWARD_EARNED = "on_reward_earned"
        const val REQUEST_KEY = "request_key_remove_banner_dialog_fragment"
    }
}