package com.example.filmaniac

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class Register2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val backBtn = findViewById<Button>(R.id.backToRegisterButton)
        val cardName = findViewById<TextView>(R.id.cardHolderTextView)
        val salary = findViewById<EditText>(R.id.salaryEditText)
        val finishBtn = findViewById<Button>(R.id.finishButton)
        val percentageEdit = findViewById<EditText>(R.id.percentageEditText)
        val percentageText = findViewById<TextView>(R.id.percentageTextView)
        val infoText = findViewById<TextView>(R.id.infoPercentageTextView)

        infoText.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Info")
            builder.setMessage(getString(R.string.perc_msg))
                .setPositiveButton("OK") { dialog, id ->
                }
            builder.show()
        }

        val intent = intent
        val name = intent.getStringExtra("fullname")
        cardName.text = name

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.baby_blue)

        backBtn.setOnClickListener {
            val goBackIntent = Intent(this, Register::class.java)
            startActivity(goBackIntent)
        }

        finishBtn.setOnClickListener {
            val perText = percentageEdit.text.toString().trim()
            percentageText.text = perText

            val salaryStr = salary.text.toString().trim()

            //To Send To the Settings Fragment
            val salaryVal = salary.text.toString().toInt()
            val percentageVal = percentageEdit.text.toString().toInt()

            if (salaryStr.isBlank()) {
                Snackbar.make(
                    it,
                    "The salary field is not filled yet.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Dismiss") {
                    }.setTextColor(resources.getColor(R.color.baby_blue_light))
                    .show()
            } else if (TextUtils.isEmpty(perText)) {

                Toast.makeText(this, perText, Toast.LENGTH_LONG).show()
                Snackbar.make(
                    it,
                    "The percentage field is not filled yet.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Dismiss") {
                    }.setTextColor(resources.getColor(R.color.baby_blue_light))
                    .show()
            } else {
                val finish = Intent(this, Home::class.java)
                startActivity(finish)
            }
        }

    }
}