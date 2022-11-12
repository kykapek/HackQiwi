package ru.teamview.hackqiwi.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.ActivityMainBinding
import ru.teamview.hackqiwi.networkUtils.ConnectionType
import ru.teamview.hackqiwi.networkUtils.NetworkMonitorUtil
import ru.teamview.hackqiwi.ui.connection.ConnectionActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private val networkMonitorUtil = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(view)

        networkMonitorUtil.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i(TAG, "Wifi Connection")
                            }
                            ConnectionType.Cellular -> {
                                Log.i(TAG, "Cellular Connection")
                            }
                            else -> { }
                        }
                    }
                    false -> {
                        Log.i(TAG, "No Connection")
                        val intent = Intent(this, ConnectionActivity::class.java)
                        this.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitorUtil.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitorUtil.unregister()
    }

    companion object {
        const val TAG = "NETWORK_MONITOR_STATUS"
    }
}