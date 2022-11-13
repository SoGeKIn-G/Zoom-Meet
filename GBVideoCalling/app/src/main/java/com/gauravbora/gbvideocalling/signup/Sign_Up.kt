package com.gauravbora.gbvideocalling.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gauravbora.gbvideocalling.databinding.SignUpBinding
import com.gauravbora.gbvideocalling.signin.Sign_In
import com.gauravbora.gbvideocalling.userDatabase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Sign_Up : AppCompatActivity() {
    private lateinit var signUpBinding: SignUpBinding
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    var emailPattern = "[a-zA-Z0-9 ._-]+@[a-z]+ \\. +[a-z]+".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpBinding = SignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        signUpBinding.signupButton.setOnClickListener {
            name = signUpBinding.nameEt.text.toString()
            email = signUpBinding.emailEt.text.toString()
            password = signUpBinding.passET.text.toString()

            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(applicationContext,"Please fill up the credential",Toast.LENGTH_SHORT).show()
            }
            else if (password.length < 6) {
                signUpBinding.passET.setError("Password less than 6")
            }
            else
            { authentication_signUp(name ,email, password) }
        }
        signUpBinding.textView.setOnClickListener {
            startActivity(Intent(  this, Sign_In:: class.java))
            finish()
        }
    }

    private fun authentication_signUp(name: String, email: String, password: String) {

        val user = User(name, email, password)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                database.collection("Users")
                    .document()
                    .set(user)
                    .addOnSuccessListener {

                        Toast.makeText(applicationContext,  "Successful regitration", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(  this, Sign_In :: class.java))
                        finish()

                    }
            } else {
                Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }

    }

    }
