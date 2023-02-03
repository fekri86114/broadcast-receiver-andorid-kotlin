package info.fekri

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import info.fekri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wifiSwitch: SwitchCompat
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiSwitch = binding.switchTurnOnOffWifi
        wifiManager = getSystemService(WIFI_SERVICE) as WifiManager

        wifiSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                wifiSwitch.text = "Wifi is ON"
            } else {
                wifiManager.isWifiEnabled = false
                wifiSwitch.text = "Wifi is OFF"
            }
        }

    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)

    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(wifiStateReceiver)

    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            when (intent!!.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_ENABLED -> {

                    wifiSwitch.isChecked= true
                    wifiSwitch.text = "Wifi is ON"
                    Toast.makeText(this@MainActivity, "Wifi is ON", Toast.LENGTH_SHORT).show()

                }
                WifiManager.WIFI_STATE_DISABLED -> {

                    wifiSwitch.text = "Wifi is OFF"
                    wifiSwitch.isChecked = false
                    Toast.makeText(this@MainActivity, "Wifi is OFF", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }

}