package info.fekri

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.fekri.databinding.ActivityMainBinding

// I wanna receive connectivity-change of internet here.
// It's better to receive data in the Activity.

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myReceiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // It's better to do the receiving-work in the onResume and onPause methods -->
    override fun onResume() {
        // I want to check version and register first,
        // then parent things
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myReceiver = MyBroadcastReceiver()
            registerReceiver(myReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }

        super.onResume()
    }

    // unregister myReceiver when application paused -->
    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

}