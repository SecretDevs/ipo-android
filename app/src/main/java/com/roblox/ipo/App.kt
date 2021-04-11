package com.roblox.ipo

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        FirebaseMessaging.getInstance().subscribeToTopic("IPO").addOnCompleteListener {
            it.let {
                Timber.d("all good")
            }
        }
    }

}