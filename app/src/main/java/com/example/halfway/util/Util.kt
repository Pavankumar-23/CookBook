package com.example.halfway.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Util {
    companion object {
        fun isNetworkConnectionAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo ?: return false
            val network = info.state
            return network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING
        }
    }
}