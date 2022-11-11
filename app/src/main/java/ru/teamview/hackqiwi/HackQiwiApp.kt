package ru.teamview.hackqiwi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HackQiwiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createTimber()
    }

    private fun createTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}