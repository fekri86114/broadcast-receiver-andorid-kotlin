package info.fekri

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.getSystemService

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // check network connection -->
        if (networkChecker(context)) {
            Toast.makeText(context, "Internet is Connected!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Internet is not Connected!", Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(context, "Received a Message!", Toast.LENGTH_SHORT).show()
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