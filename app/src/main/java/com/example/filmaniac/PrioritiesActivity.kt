package com.example.filmaniac

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.filmaniac.model.Item
import com.example.filmaniac.ui.Home
import com.example.filmaniac.ui.Register
import com.example.filmaniac.ui.Register2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PrioritiesActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_priorities)

        val itemNameEditTxt = findViewById<EditText>(R.id.itemListEditText)
        val priceEditTxt = findViewById<EditText>(R.id.priceEditText)
        val addBtn = findViewById<Button>(R.id.addItemButton)
        val billsLayout = findViewById<ConstraintLayout>(R.id.billsLayout)
        val phoneLayout = findViewById<ConstraintLayout>(R.id.phoneLayout)
        val internetLayout = findViewById<ConstraintLayout>(R.id.internetLayout)
        val doneBtn = findViewById<Button>(R.id.doneButton)
        val backBtn = findViewById<Button>(R.id.backToRegister2Button)


        val dialog = Dialog(this)
        dialog.setContentView(R.layout.priorities_info_dialog)
        dialog.show()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        reference = userReference.child("Items")


        billsLayout.setOnClickListener {
            val bills = "Bills"
            itemNameEditTxt.setText(bills)
        }

        phoneLayout.setOnClickListener {
            val phone = "Phone"
            itemNameEditTxt.setText(phone)
        }

        internetLayout.setOnClickListener {
            val internet = "Internet"
            itemNameEditTxt.setText(internet)
        }

        addBtn.setOnClickListener {
            val itemString = itemNameEditTxt.text.toString().trim()
            val priceString = priceEditTxt.text.toString().trim()
            val price = priceString.toInt()

            val item = Item(itemString, price)
            reference.push().setValue(item)

            itemNameEditTxt.text.clear()
            priceEditTxt.text.clear()

        }

        doneBtn.setOnClickListener {
            val done = Intent(this, Home::class.java)
            startActivity(done)
        }

        backBtn.setOnClickListener {
            val goBack = Intent(this, Register2::class.java)
            startActivity(goBack)
        }

    }
}