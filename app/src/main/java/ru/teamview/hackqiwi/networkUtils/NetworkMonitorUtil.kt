package ru.teamview.hackqiwi.networkUtils;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

enum class ConnectionType {
    Wifi, Cellular
}

class NetworkMonitorUtil(context: Context) {

    private var mContext = context

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    lateinit var result: ((isAvailable: Boolean, type: ConnectionType?) -> Unit)

    @Suppress("DEPRECATION")
    fun register() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (connectivityManager.activeNetwork == null) {
                result(false,null)
            }
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    result(false, null)
                }

                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    when {
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result(true,ConnectionType.Wifi)
                        }
                        else -> {
                            result(true,ConnectionType.Cellular)
                        }
                    }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            mContext.registerReceiver(networkChangeReceiver, intentFilter)
        }
    }

    fun unregister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val connectivityManager =
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            mContext.unregisterReceiver(networkChangeReceiver)
        }
    }

    @Suppress("DEPRECATION")
    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            if (activeNetworkInfo != null) {
                when (activeNetworkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        result(true, ConnectionType.Wifi)
                    }
                    else -> {
                        result(true, ConnectionType.Cellular)
                    }
                }
            } else {
                result(false, null)
            }
        }
    }
}