package com.example.filmaniac.ui

import android.app.AlertDialog
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
import com.example.filmaniac.SettingsFragment
import com.example.filmaniac.model.Info
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Register2 : AppCompatActivity() {

    private val settingsFragment = SettingsFragment()

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""

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
            builder.setIcon(R.drawable.human_bot)
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
            val salaryStr = salary.text.toString().trim()

            //To Send To the Settings Fragment
            val salaryVal = salaryStr.toInt()
            val percentageVal = perText.toInt()

            val bundle = Bundle()
            bundle.putInt("percentage", percentageVal)
            bundle.putInt("salary", salaryVal)
            settingsFragment.arguments = bundle


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

            database = FirebaseDatabase.getInstance()

            auth = FirebaseAuth.getInstance()
            userId = auth.currentUser!!.uid
            userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

            reference = userReference.child("Info")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val i = Info(percentageVal, salaryVal, null)
                    reference.setValue(i)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


    }
}