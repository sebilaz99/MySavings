package com.example.filmaniac.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.filmaniac.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val backBtn = findViewById<Button>(R.id.backToLoginButton)
        val emailEditTxt = findViewById<EditText>(R.id.emailEditText)
        val submitBtn = findViewById<Button>(R.id.submitButton)

        auth = FirebaseAuth.getInstance()

        val email = emailEditTxt.text.toString().trim()

        //BUG: NU-MI IA EMAILUL !!!
        submitBtn.setOnClickListener {
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please type your email", Toast.LENGTH_SHORT).show()
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Please check your e-mail", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong! Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        backBtn.setOnClickListener {
            val goBackIntent = Intent(this, Login::class.java)
            startActivity(goBackIntent)
        }

    }
}