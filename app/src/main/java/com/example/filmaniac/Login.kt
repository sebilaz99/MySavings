package com.example.filmaniac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val logButton = findViewById<Button>(R.id.loginButton)
        val registerText = findViewById<TextView>(R.id.createAccountTextView)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.baby_blue)

        registerText.setOnClickListener {
            val goToRegister = Intent(this, Register::class.java)
            startActivity(goToRegister)
        }

        auth = FirebaseAuth.getInstance()

        logButton.setOnClickListener {
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString().trim()

            if (TextUtils.isEmpty(emailStr)) {
                Toast.makeText(this, "Please type your email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(this, "Please type your password", Toast.LENGTH_SHORT).show()
            } else if (!TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(passwordStr)) {
                auth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                        } else {
                            email.setTextColor(resources.getColor(R.color.red))
                            password.setTextColor(resources.getColor(R.color.red))

                            Toast.makeText(
                                this,
                                "Your credentials are incorrect!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
            }
        }

    }
}