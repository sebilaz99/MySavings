package com.example.filmaniac.fragments.myLists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmaniac.R
import com.example.filmaniac.model.Item
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyListsAdapter(
    items:
    FirebaseRecyclerOptions<Item>, val clickListener: (Item, Int) -> Unit
) :
    FirebaseRecyclerAdapter<Item, MyListsAdapter.MyListsViewHolder>(items) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mylist_item, parent, false)

        return MyListsViewHolder(view)
    }


    inner class MyListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.listTextView)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTextView)

    }


    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int, item: Item) {
        holder.nameTxt.text = item.name
        holder.priceTxt.text = item.price.toString()

        holder.itemView.setOnLongClickListener {
            clickListener(item, position)
            true
        }
    }


}