package ru.teamview.hackqiwi

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HackQiwiApp : Application() {

    private var prefs: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()
        createTimber()
        prefs = getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        instance = this
    }

    private fun createTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun saveAuthToken(token: String) {
        val editor = prefs?.edit()
        editor?.putString(TOKEN, token)
        editor?.commit()
    }

    fun getAuthToken(): String? {
        return prefs?.getString(TOKEN, "")
    }

    fun saveJwt(token: String) {
        val editor = prefs?.edit()
        editor?.putString(JWT_TOKEN, token)
        editor?.commit()
    }

    fun getJwt(): String? {
        return prefs?.getString(JWT_TOKEN, "")
    }

    fun savePhone(phone: String) {
        val editor = prefs?.edit()
        editor?.putString(PHONE, phone)
        editor?.commit()
    }

    fun getPhone(): String? {
        return prefs?.getString(PHONE, "")
    }

    fun savePassword(phone: String) {
        val editor = prefs?.edit()
        editor?.putString(PASSWORD, phone)
        editor?.commit()
    }

    fun getPassword(): String? {
        return prefs?.getString(PASSWORD, "")
    }

    companion object {
        private lateinit var instance: HackQiwiApp

        fun getInstance() : HackQiwiApp = instance
    }
}