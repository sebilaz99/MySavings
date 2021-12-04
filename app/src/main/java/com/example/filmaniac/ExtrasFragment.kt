package com.example.filmaniac

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ExtrasFragment : Fragment(R.layout.fragment_extras) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.tipsRecyclerView)

        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter


    }


}