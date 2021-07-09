package com.tekinumut.eruyemekhane.ui.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tekinumut.eruyemekhane.BuildConfig
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.base.BaseFragmentDB
import com.tekinumut.eruyemekhane.databinding.FragmentAboutBinding
import com.tekinumut.eruyemekhane.utils.Utility

class AboutFragment : BaseFragmentDB<FragmentAboutBinding>(R.layout.fragment_about) {

    private val viewModel by viewModels<AboutViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        init()
        setListeners()
    }

    private fun init() {
        // prevent animation plays on configuration changes
        if (!viewModel.isLottiePlayed) {
            binding.lottieView.playAnimation()
            viewModel.isLottiePlayed = true
        }
    }

    private fun setListeners() {
        binding.sendFeedback.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.gmail_address)))
            startActivity(Intent.createChooser(intent, getString(R.string.send_mail_title)))
        }
        binding.rateApp.setOnClickListener {
            val packageName = BuildConfig.APPLICATION_ID.substringBeforeLast(".debug")
            val nativeStorePage = "market://details?id=$packageName"
            val storagePageUrl = "https://play.google.com/store/apps/details?id=$packageName"
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(nativeStorePage)))
            } catch (e: ActivityNotFoundException) {
                // if there is no google play app on the phone
                Utility.openWebSiteWithCustomTabs(it.context, storagePageUrl)
            }
        }
        binding.githubPage.setOnClickListener {
            val githubAddress = it.context.getString(R.string.github_address)
            Utility.openWebSiteWithCustomTabs(it.context, githubAddress)
        }
    }
}