package com.example.filmaniac.fragments.myLists

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyListsFragment : Fragment(R.layout.fragment_my_lists) {

    private lateinit var database: FirebaseDatabase
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
        val deleteBtn = view.findViewById<ConstraintLayout>(R.id.clearListLayout)
        val itemName = view.findViewById<EditText>(R.id.itemListEditText)
        val itemPrice = view.findViewById<EditText>(R.id.priceEditText)
        val totalPriceTxt = view.findViewById<TextView>(R.id.totalPriceTextView)

        database = FirebaseDatabase.getInstance()

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

        recyclerView.adapter = MyListsAdapter(options) { i: Item, pos: Int ->
            Toast.makeText(view.context, pos.toString(), Toast.LENGTH_SHORT).show()
        }

        reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalPrice = 0
                    val keysList = ArrayList<String>()
                    val valList = ArrayList<String>()

                    snapshot.children.forEach {
                        val key = it.key
                        val value = it.value.toString()
                        keysList.add(key.toString())
                        valList.add(value)

                    }

                    if (valList.size > 0) {
                        val stringFromArray = valList.joinToString()
                        val idk = stringFromArray.replace("{", "").replace("}", "").replace(" ", "")
                            .replace("price=", "").replace("name=", "")

                        val values = idk.split(",") as ArrayList<String>

                        for (i in 0 until values.size step 2) {
                            totalPrice += values[i].toInt()
                        }
                        totalPriceTxt.text = totalPrice.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }
        )



        addBtn.setOnClickListener {
            val itemString = itemName.text.toString().trim()
            val priceString = itemPrice.text.toString().trim()
            val price = priceString.toInt()

            reference = userReference.child("Items")
            val item = Item(itemString, price)
            reference.push().setValue(item)
        }


        deleteBtn.setOnClickListener {
            reference.removeValue()
            itemsList.removeAll(itemsList.toSet())
            totalPriceTxt.text = "0"
        }


    }


}
