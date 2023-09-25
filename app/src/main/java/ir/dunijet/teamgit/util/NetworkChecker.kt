package ir.dunijet.teamgit.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkChecker(private val context: Context) {

    val isInternetConnected :Boolean get() {
            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork
            ?: return false

        val myNetwork = connectivityManager.getNetworkCapabilities( networkCapabilities )
            ?: return false

        result = when {
            myNetwork.hasTransport( NetworkCapabilities.TRANSPORT_WIFI ) -> true
            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
        }
    val isWifiConnected :Boolean get() {


            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val myNetwork = connectivityManager.getNetworkCapabilities( networkCapabilities ) ?: return false

        result = when {

            myNetwork.hasTransport( NetworkCapabilities.TRANSPORT_WIFI ) -> true

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false

            else -> false

        }


        return result

        }
    val isMobileDataConnected :Boolean get() {


            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val myNetwork = connectivityManager.getNetworkCapabilities( networkCapabilities ) ?: return false

        result = when {

            myNetwork.hasTransport( NetworkCapabilities.TRANSPORT_WIFI ) -> false

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false

            else -> false

        }


        return result

        }
    val isEthernetConnected :Boolean get() {


            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val myNetwork = connectivityManager.getNetworkCapabilities( networkCapabilities ) ?: return false

        result = when {

            myNetwork.hasTransport( NetworkCapabilities.TRANSPORT_WIFI ) -> false

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false

            myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false

        }


        return result

        }

}