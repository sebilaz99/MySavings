package com.example.filmaniac

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

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

        val bundle: Bundle? = arguments
        val percentageReceived = bundle?.getInt("percentage")
        val salaryReceived = bundle?.getInt("salary")

        percentageTxt.text = percentageReceived.toString()
        salaryEditTxt.setText(salaryReceived.toString())

        percentageTxt.text = percentage.toString()

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
    }

}
