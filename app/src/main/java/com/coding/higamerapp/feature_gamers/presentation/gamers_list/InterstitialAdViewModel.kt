package com.coding.higamerapp.feature_gamers.presentation.gamers_list

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.coding.higamerapp.common.util.showInterstitial
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class InterstitialAdViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    var mInterstitialAd: InterstitialAd? = null
    private var adRequest: AdRequest = AdRequest.Builder().build()

    private fun loadInterstitial(context: Context) {
        InterstitialAd.load(context,
            "ca-app-pub-1815535014308108/8039596995",
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    showInterstitial = true
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
    }

    fun launchAd(context: Context) {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(context as Activity)

        }
    }

    init {
        loadInterstitial(context)
    }
}