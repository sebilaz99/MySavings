package com.example.filmaniac.myLists

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyListsFragment : Fragment(R.layout.fragment_my_lists) {

    private lateinit var reference: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""
    private lateinit var itemsList: ArrayList<Item>
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addBtn = view.findViewById<Button>(R.id.addItemButton)
        val itemName = view.findViewById<EditText>(R.id.itemListEditText)

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)


        recyclerView = view.findViewById(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)

        itemsList = arrayListOf()

        reference = userReference.child("Items")

        val options = FirebaseRecyclerOptions.Builder<Item>()
            .setQuery(reference, Item::class.java)
            .build()

        recyclerView.adapter = MyListsAdapter(options)

        var itemIndex = 0
        addBtn.setOnClickListener {
            val itemString = itemName.text.toString().trim()
            val itemPath = "item$itemIndex$userId"
            reference = userReference.child("Items").child(itemPath)
            val item = Item(itemString)
            reference.setValue(item)
            itemIndex++
        }

    }


}
