package com.example.filmaniac

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var percentage = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val percentageTxt = view.findViewById<TextView>(R.id.percentageTextView)
        val salaryEditTxt = view.findViewById<EditText>(R.id.salaryEditText)
        val chooseDayBtn = view.findViewById<Button>(R.id.chooseDayButton)
        val higherBtn = view.findViewById<Button>(R.id.higherButton)
        val lowerBtn = view.findViewById<Button>(R.id.lowerButton)

        val bundle = arguments

        val percentageReceived = bundle?.getInt("percentage")
        val salaryReceived = bundle?.getString("salary")

        percentageTxt.text = percentageReceived.toString()
        salaryEditTxt.setText(salaryReceived.toString())


        lowerBtn.setOnClickListener {
            if (percentage > 0) {
                percentage -= 1
                percentageTxt.text = percentage.toString()
            }
        }

        higherBtn.setOnClickListener {
            if (percentage < 100) {
                percentage += 1
                percentageTxt.text = percentage.toString()
            }
        }

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
            chooseDayBtn.text = sdf.format(calendar.time)
        }

        chooseDayBtn.setOnClickListener {
            DatePickerDialog(view.context, datePicker, cYear, cMonth, cDay).show()
        }


    }
}


