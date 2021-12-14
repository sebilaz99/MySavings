package com.example.filmaniac.myLists

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.example.filmaniac.model.User
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MyListsFragment : Fragment(R.layout.fragment_my_lists) {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId = ""
    private lateinit var itemsList: ArrayList<User>
    private lateinit var recyclerView: RecyclerView


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addBtn = view.findViewById<Button>(R.id.addItemButton)
        val deleteBtn = view.findViewById<ConstraintLayout>(R.id.clearListLayout)
        val itemName = view.findViewById<EditText>(R.id.itemListEditText)

        database = FirebaseDatabase.getInstance()
        database.reference.child("Users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val item = dataSnapshot.getValue<User>()
                    if (item != null) {
                        itemsList.add(item)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("MyListsFragment: ", "Failed to read value.", error.toException())
                }
            })



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
            .setLifecycleOwner(this)
            .build()

        recyclerView.adapter = MyListsAdapter(options)


        addBtn.setOnClickListener {
            val itemString = itemName.text.toString().trim()
            reference = userReference.child("Items")
            val item = Item(itemString)
            reference.push().setValue(item)
        }

        deleteBtn.setOnClickListener {
           reference.removeValue()
        }

    }


}
