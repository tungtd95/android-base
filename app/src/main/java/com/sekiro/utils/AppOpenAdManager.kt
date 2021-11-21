package com.sekiro.utils

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.sekiro.MainApplication
import com.sekiro.R
import com.sekiro.ui.base.BaseActivity
import java.util.*

/**
 * Prefetches App Open Ads.
 */
class AppOpenManager(private val myApplication: MainApplication) : ActivityLifecycleCallbacks,
    LifecycleObserver {
    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAdLoadCallback? = null
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0
    private var lastShow: Long = 0
    private val handler = Handler(Looper.getMainLooper())
    private var activityState = ActivityState.UN_AVAILABLE

    /**
     * Request an ad
     */
    fun fetchAd() {
        if (isAdAvailable) {
            return
        }
        loadCallback = object : AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             *
             * @param ad the loaded app open ad.
             */
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
                Log.i(LOG_TAG, "onAdLoaded")
                showAdIfAvailable()
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the error.
                Log.i(LOG_TAG, "onAdFailedToLoad" + loadAdError.message)
            }
        }
        val request = adRequest
        AppOpenAd.load(
            myApplication,
            myApplication.getString(R.string.ad_unit_id_full_screen),
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            loadCallback!!
        )
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (System.currentTimeMillis() - lastShow < ADD_LOADING_INTERVAL * 60 * 1000) {
            return
        }

        Log.d(LOG_TAG, "showAdIfAvailable: $isShowingAd $isAdAvailable")
        if (!isShowingAd && isAdAvailable && activityState == ActivityState.AVAILABLE) {
            Log.d(LOG_TAG, "Will show ad.")
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {}
                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
            currentActivity?.let {
                appOpenAd?.show(it)
                lastShow = System.currentTimeMillis()
            }
        }
    }

    /**
     * Creates and returns ad request.
     */
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    private val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo()

    private fun wasLoadTimeLessThanNHoursAgo(): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 60000
        return dateDifference < numMilliSecondsPerHour * ADD_LOADING_INTERVAL
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
        activityState = if ((activity as? BaseActivity<*>)?.shouldShowAd == true) {
            ActivityState.AVAILABLE
        } else {
            ActivityState.UN_AVAILABLE
        }
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        activityState = if ((activity as? BaseActivity<*>)?.shouldShowAd == true) {
            ActivityState.AVAILABLE
        } else {
            ActivityState.UN_AVAILABLE
        }
        showAdIfAvailable()
    }

    override fun onActivityStopped(activity: Activity) {
        activityState = ActivityState.UN_AVAILABLE
    }

    override fun onActivityPaused(activity: Activity) {
        activityState = ActivityState.UN_AVAILABLE
        handler.removeCallbacksAndMessages(null)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        activityState = ActivityState.UN_AVAILABLE
        currentActivity = null
    }

    companion object {
        private const val LOG_TAG = "AppOpenManager"
        private var isShowingAd = false

        private const val ADD_LOADING_INTERVAL = 1L // minutes
    }

    /**
     * Constructor
     */
    init {
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}

enum class ActivityState {
    AVAILABLE, UN_AVAILABLE
}