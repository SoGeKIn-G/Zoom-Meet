package com.gauravbora.gbvideocalling.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gauravbora.gbvideocalling.MainActivity
import com.gauravbora.gbvideocalling.R
import com.gauravbora.gbvideocalling.signin.Sign_In
import com.gauravbora.gbvideocalling.signup.Sign_Up


class Splash_Screen : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide()


        if (isConnected() == false) {
            var builder = AlertDialog.Builder(applicationContext)
            builder.setTitle("No Internet connection")
            builder.setMessage("Do You wanna exit?")
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                finish()
                System.exit(0)
            }


            builder.setNegativeButton("Check Wifi") { dialogInterface, which ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                finish()
                System.exit(0)
            }


            var alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else {
//            yukctrjtyrxcyjv5yjeyjrv
            handler = Handler()
            handler.postDelayed({
                val intent = Intent(applicationContext, MainActivity ::class.java)
                startActivity(intent)
                finish()
            }, 2000)

        }
    }


    fun isConnected(): Boolean {
        var connectivityManager: ConnectivityManager =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var network: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (network != null) {
            if (network.isConnected) {
                return true
            }
        }
        return false
    }
}

