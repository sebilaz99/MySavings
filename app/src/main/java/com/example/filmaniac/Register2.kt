package com.example.filmaniac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat

class Register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val cardName = findViewById<TextView>(R.id.cardHolderTextView)

        val intent = intent
        val name = intent.getStringExtra("fullname")
        cardName.text = name

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.baby_blue)
    }
}