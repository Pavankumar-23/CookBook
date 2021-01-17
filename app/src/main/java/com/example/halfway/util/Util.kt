package com.example.halfway.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Util {
    companion object {
        fun isNetworkConnectionAvailable(context: Context): Boolean {
            val connectionManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectionManager.activeNetwork ?: return false
            val capabilities =
                connectionManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

        fun nullToEmpty(value: String?): String {
            val default = ""
            value?.let {
                return value
            }
            return default
        }
    }
}