package com.coding.higamerapp

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.coding.higamerapp.common.components.MyScreen
import com.coding.higamerapp.ui.theme.hiGamerAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ActivityViewModel by viewModels()

    private lateinit var adView: AdView

    private var initialLayoutComplete = false

    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            val adView = findViewById<FrameLayout>(R.id.adView_container)
            var adWidthPixels = adView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    lateinit var navController: NavController

    @ExperimentalPagerApi
    @RequiresApi(Build.VERSION_CODES.S)
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HiGamerApp)
        setContentView(R.layout.activity_layout).apply {
            MobileAds.initialize(applicationContext)
            adView = AdView(applicationContext)
            val container = findViewById<FrameLayout>(R.id.adView_container)
            container.addView(adView)
            container.viewTreeObserver.addOnGlobalLayoutListener {
                if (!initialLayoutComplete) {
                    initialLayoutComplete = true
                   loadBanner()
                }
            }
            findViewById<ComposeView>(R.id.composeView).setContent {
                navController = rememberNavController()
                hiGamerAppTheme {
                    MyScreen(navController as NavHostController, viewModel)
                }
            }
        }
    }


    override fun onStop() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.deleteGamerFromDatabase()
        }
        viewModel.stopRepeatingTask()
        super.onStop()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }


    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startRepeatingTask()
        adView.resume()
    }

    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID

        adView.adSize = adSize

        // Create an ad request.
        val adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private const val AD_UNIT_ID = "ID"
    }
}


