package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val quakes: List<Feature>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (Feature) -> Unit

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvTit: TextView = view.findViewById(R.id.textViewTit)

        fun bind(feature: Feature) {
            tvTit.text = feature.properties.title

            view.setOnClickListener {
                onItemClickListener(feature)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return quakes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quake = quakes[position]
        holder.bind(quake)
    }
}