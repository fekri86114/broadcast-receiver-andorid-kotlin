package info.fekri

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import info.fekri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkNetworkConnection()
            }
        }

        val intentFilter = IntentFilter("android.conn.CONNECTIVITY_CHANGE")
        registerReceiver(
            broadcastReceiver,
            intentFilter
        ) // register 'broadcastReceiver' with 'CONNECTIVITY intent'

    }

    private fun checkNetworkConnection() {

        if (NetworkChecker(this).isInternetConnected)
            binding.txtShowNetwork.text = "Internet Connected"
        else
            binding.txtShowNetwork.text = "Internet Disconnected"

    }
    // unregister the broadcastReceiver
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}