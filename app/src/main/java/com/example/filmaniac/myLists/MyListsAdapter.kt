package com.example.filmaniac.myLists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.example.filmaniac.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyListsAdapter(
    items:
    FirebaseRecyclerOptions<Item>
) :
    FirebaseRecyclerAdapter<Item, MyListsAdapter.MyListsViewHolder>(items) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyListsAdapter.MyListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mylist_item, parent, false)
        return MyListsViewHolder(view)
    }


    inner class MyListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.listTextView)
    }


    override fun onBindViewHolder(holder: MyListsViewHolder, p1: Int, p2: Item) {
        holder.nameTxt.text = p2.name
    }

}