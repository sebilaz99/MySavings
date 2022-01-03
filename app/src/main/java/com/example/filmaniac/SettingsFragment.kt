package com.example.filmaniac

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""

    private var percentage = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val percentageTxt = view.findViewById<TextView>(R.id.percentageTextView)
        val salaryEditTxt = view.findViewById<EditText>(R.id.salaryEditText)
        val chooseDayBtn = view.findViewById<Button>(R.id.chooseDayButton)
        val higherBtn = view.findViewById<Button>(R.id.higherButton)
        val lowerBtn = view.findViewById<Button>(R.id.lowerButton)
        val saveSalaryBtn = view.findViewById<Button>(R.id.saveSalaryButton)

        val calendar = Calendar.getInstance()

        val cDay = calendar.get(Calendar.DAY_OF_MONTH)
        val cMonth = calendar.get(Calendar.MONTH)
        val cYear = Year.now().value

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val format = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(format, Locale.UK)
            val dateStr = sdf.format(calendar.time)
            chooseDayBtn.text = dateStr
            Toast.makeText(view.context, "Your salary date has been saved!", Toast.LENGTH_SHORT)
                .show()

            database = FirebaseDatabase.getInstance()

            auth = FirebaseAuth.getInstance()
            userId = auth.currentUser!!.uid
            userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

            reference = userReference.child("Info")
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reference.child("date").setValue(dateStr)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        reference = userReference.child("Info")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val percentage = snapshot.child("percentage").value
                var percentageVal = percentage.toString().toInt()
                percentageTxt.text = percentage.toString()

                lowerBtn.setOnClickListener {
                    if (percentageVal > 0) {
                        percentageVal -= 1
                        percentageTxt.text = percentageVal.toString()
                        reference.child("percentage").setValue(percentageVal)
                    }
                }

                higherBtn.setOnClickListener {
                    if (percentageVal < 100) {
                        percentageVal += 1
                        percentageTxt.text = percentageVal.toString()
                        reference.child("percentage").setValue(percentageVal)
                    }
                }

                val salary = snapshot.child("salary").value
                salaryEditTxt.setText(salary.toString())

                saveSalaryBtn.setOnClickListener {
                    val salaryNew = salaryEditTxt.text.toString().trim()
                    reference.child("salary").setValue(salaryNew)
                    Toast.makeText(view.context, "Your salary has been saved!", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        chooseDayBtn.setOnClickListener {
            DatePickerDialog(view.context, datePicker, cYear, cMonth, cDay).show()
        }

    }

}


