package ir.ha.meproject.common.extensions

import android.content.Context
import android.net.ConnectivityManager

/** ADD -> <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> */
fun isInternetConnected(context: Context?): Boolean {
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
