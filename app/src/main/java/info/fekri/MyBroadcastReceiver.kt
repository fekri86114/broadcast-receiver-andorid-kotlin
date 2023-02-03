package info.fekri

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast



class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (networkChecker(context!!)) {
            Toast.makeText(context, "Internet is Connected!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Internet is not Connected!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun networkChecker(mContext: Context): Boolean {

        try {
            val conManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = conManager.activeNetworkInfo

            return (nInfo != null && nInfo.isConnected)

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

}