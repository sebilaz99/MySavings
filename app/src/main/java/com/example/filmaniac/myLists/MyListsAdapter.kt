package com.example.filmaniac.myLists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyListsAdapter(items: FirebaseRecyclerOptions<Item>) :
    FirebaseRecyclerAdapter<Item, MyListsAdapter.MyListsViewHolder>(items)
    {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyListsAdapter.MyListsViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.mylist_item, parent, false)
            return MyListsViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyListsAdapter.MyListsViewHolder, position: Int, item: Item) {
            holder.nameTxt.text = item.name
        }

        inner class MyListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTxt = itemView.findViewById<TextView>(R.id.listTextView)
        }

    }