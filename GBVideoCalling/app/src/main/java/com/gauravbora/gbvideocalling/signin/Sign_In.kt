package com.gauravbora.gbvideocalling.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gauravbora.gbvideocalling.MainActivity
import com.gauravbora.gbvideocalling.databinding.SignInBinding
import com.gauravbora.gbvideocalling.signup.Sign_Up
import com.google.firebase.auth.FirebaseAuth

class Sign_In : AppCompatActivity() {

    private lateinit var signInBinding: SignInBinding
    lateinit var email: String
    lateinit var password: String
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = SignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        signInBinding.SignInbutton.setOnClickListener {
            email = signInBinding.emailEt.text.toString()
            password = signInBinding.passEt.text.toString()

//            Toast.makeText(applicationContext, "$email $password", Toast.LENGTH_SHORT).show()


            if (email.isBlank() || password.isBlank()) {
                signInBinding.passEt.setError("Blank")
                signInBinding.emailEt.setError("Blank")
                Toast.makeText(applicationContext, "Please Enter credential", Toast.LENGTH_SHORT)
                    .show()
            }
            if (password.length < 6) {
                signInBinding.passEt.setError(" Password Is less than 6")
            } else {
                authentication_Sign(email, password)
            }
        }

        signInBinding.textView12.setOnClickListener {
            startActivity(Intent(this, Sign_Up::class.java))
            finish()

        }
    }

    fun authentication_Sign(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Log in Successful", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, " User Don't Exist", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }


}
