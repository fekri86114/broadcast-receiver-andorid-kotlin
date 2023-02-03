package info.fekri

import android.Manifest.permission.RECEIVE_SMS
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import info.fekri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myReceiver: MyBroadcastReceiver
    private val SMS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequest.setOnClickListener {

            if (
                ContextCompat
                    .checkSelfPermission(this, RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            ) {

                requestSMSPermission()

            } else {
                Toast.makeText(this, "Permission has been Received!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun requestSMSPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECEIVE_SMS)) {

            AlertDialog.Builder(this)
                .setTitle("Request Permission")
                .setMessage("Confirm the Permission to check SMS.")
                .setPositiveButton("GO") { dialog, which -> reqPermission() }
                .setNegativeButton("NO") { dialog, which -> dialog.dismiss() }
                .create()
                .show()

        } else {
            reqPermission()
        }
    }

    private fun reqPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(RECEIVE_SMS), SMS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Confirmed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myReceiver = MyBroadcastReceiver()
            registerReceiver(myReceiver, IntentFilter("android.conn.CONNECTIVITY_CHANGE"))
        }

        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(myReceiver)
        super.onPause()
    }

}