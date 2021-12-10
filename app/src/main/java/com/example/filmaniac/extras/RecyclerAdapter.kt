package com.example.filmaniac.extras

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val tipNumbers = arrayOf("Tip #1", "Tip #2", "Tip #3")
    val tipTexts = arrayOf(
        "What should I begin with?",
        "Where can I modify the salary and the salary percentage",
        "Think about it later"
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.tip_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = tipNumbers[position]
        holder.text.text = tipTexts[position]

    }

    override fun getItemCount(): Int {
        return tipNumbers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var number = itemView.findViewById<TextView>(R.id.tipNumber)
        var text = itemView.findViewById<TextView>(R.id.tipText)

        lateinit var dialog: Dialog

        init {
            itemView.setOnClickListener {
                when (position) {
                    0 -> {
                        dialog = Dialog(itemView.context)
                        dialog.setContentView(R.layout.custom_popup_0)
                        dialog.show()
                    }
                }
            }
        }
    }
}




