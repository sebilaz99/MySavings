package com.example.filmaniac.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.filmaniac.R
import com.example.filmaniac.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userReference: DatabaseReference
    private var userId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullname = findViewById<EditText>(R.id.fullnameEditText)
        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val passwordConf = findViewById<EditText>(R.id.confirmEditText)
        val regButton = findViewById<Button>(R.id.registerButton)
        val loginText = findViewById<TextView>(R.id.backToLoginTextView)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.baby_blue)

        auth = FirebaseAuth.getInstance()

        regButton.setOnClickListener {
            val fullnameStr = fullname.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString().trim()
            val passwordConfStr = passwordConf.text.toString().trim()

            if (TextUtils.isEmpty(fullnameStr)) {
                Toast.makeText(this, "Please type your full name", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(emailStr)) {
                Toast.makeText(this, "Please type your email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(this, "Please type your password", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(passwordConfStr)) {
                Toast.makeText(this, "Please re-type your password", Toast.LENGTH_SHORT).show()
            } else if (passwordStr.length < 8) {
                Toast.makeText(
                    this,
                    "Password must contains at least 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (passwordStr != passwordConfStr) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            } else if (!TextUtils.isEmpty(fullnameStr) && !TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(
                    passwordStr
                ) && !TextUtils.isEmpty(passwordConfStr) && passwordStr.length >= 8 && passwordStr == passwordConfStr
            ) {
                auth.createUserWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            userId = auth.currentUser!!.uid
                            userReference = FirebaseDatabase.getInstance().reference.child("Users")
                                .child(userId)

                            val user = User(userId, fullnameStr, emailStr, passwordStr)
                            userReference.setValue(user)

                            val intent = Intent(this, Register2::class.java)
                            intent.putExtra("fullname", fullnameStr)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Error while creating your account",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }

        loginText.setOnClickListener {
            val goToLogin = Intent(this, Login::class.java)
            startActivity(goToLogin)
        }

    }


}