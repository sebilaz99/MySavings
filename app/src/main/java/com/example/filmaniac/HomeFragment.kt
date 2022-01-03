package com.example.filmaniac

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentResultListener
import com.example.filmaniac.ui.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var database: FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var infoReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullnameTxt = view.findViewById<TextView>(R.id.fullnameTextView)
        val signOutTxt = view.findViewById<TextView>(R.id.signOutTextView)
        val priceTxt = view.findViewById<TextView>(R.id.totalPriceTextView)
        val salaryTxt = view.findViewById<TextView>(R.id.salaryTextView)
        val resultTxt = view.findViewById<TextView>(R.id.resultTextView)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        userReference.get().addOnSuccessListener {
            if (it.exists()) {
                val fullname = it.child("name").value as String
                val finalName = fullname.replaceAfter(" ", "")
                fullnameTxt.text = finalName
            }
        }

        signOutTxt.setOnClickListener {
            val signout = Intent(it.context, Login::class.java)
            startActivity(signout)
            auth.signOut()
        }

        infoReference = userReference.child("Info")

        infoReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val salary = snapshot.child("salary").value
                val total = snapshot.child("total").value
                val salaryVal = salary.toString().toInt()
                val totalVal = total.toString().toInt()
                salaryTxt.text = salaryVal.toString()
                priceTxt.text = totalVal.toString()
                val result = salaryVal - totalVal
                resultTxt.text = result.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}