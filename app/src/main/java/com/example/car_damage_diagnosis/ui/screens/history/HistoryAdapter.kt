package com.example.car_damage_diagnosis.ui.screens.history

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_damage_diagnosis.R

class HistoryAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val items = List(10) { "Item $it" }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_card, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            Log.d("HistoryAdapter", "Item clicked: ${items[position]}")
            onClick() // Проверка, вызывается ли onClick
        }
    }

    override fun getItemCount(): Int = items.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)

        fun bind(data: String) {
            title.text = data
            description.text = "Description for $data"
        }
    }
}
