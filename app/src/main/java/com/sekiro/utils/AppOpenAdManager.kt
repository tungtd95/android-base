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

    init {
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun fetchAd() {
        if (isAdAvailable) {
            return
        }
        loadCallback = object : AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
                Log.i(LOG_TAG, "onAdLoaded")
                showAdIfAvailable()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
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

    fun showAdIfAvailable() {
        if (!wasLoadTimeLongerThanIntervalTimeAgo()) {
            return
        }

        Log.d(LOG_TAG, "showAdIfAvailable: $isShowingAd $isAdAvailable")
        if (!isShowingAd && isAdAvailable && activityState == ActivityState.AVAILABLE) {
            Log.d(LOG_TAG, "Will show ad.")
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
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

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    private val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLongerThanIntervalTimeAgo()

    private fun wasLoadTimeLongerThanIntervalTimeAgo(): Boolean {
        return System.currentTimeMillis() - lastShow > ADD_LOADING_INTERVAL * 60 * 1000
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

        private const val ADD_LOADING_INTERVAL = 0.5 // minutes
    }
}

enum class ActivityState {
    AVAILABLE, UN_AVAILABLE
}