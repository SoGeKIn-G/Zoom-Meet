package com.gauravbora.gbvideocalling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gauravbora.gbvideocalling.databinding.ActivityMainBinding
import com.gauravbora.gbvideocalling.signin.Sign_In
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            Toast.makeText(applicationContext, "Please Log In ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Sign_In::class.java))
            finish()
        }

        val serverUrl: URL
        try {
            serverUrl = URL("https://meet.jit.si")
            val options = JitsiMeetConferenceOptions.Builder().setServerURL(serverUrl)
                .setFeatureFlag("welcome enabled", false).build()
            JitsiMeet.setDefaultConferenceOptions(options)
        } catch (e: MalformedURLException) {
            Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_SHORT).show()
        }
        mainBinding.joinBtn.setOnClickListener {
            val options =
                JitsiMeetConferenceOptions.Builder().setRoom(mainBinding.codeBox.text.toString())
                    .setFeatureFlag("welcome enabled", false).build();
            JitsiMeetActivity.launch(this, options)
        }
        mainBinding.logOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Sign_In::class.java))
            finish()
        }


    }
}