package gaurang.patel.kotlin.githubtrendingandroid.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtil() {

    fun isNetworkAvailable(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var isConnected: Boolean = false;
        if (activeNetwork != null) {
            isConnected = activeNetwork?.isConnected == true
        } else {
            isConnected = false
        }
        return isConnected
    }
}