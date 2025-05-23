package com.example.teamproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchPlayerAdapter(
    private val players: List<Triple<String, String, Player>>, // teamName, logo, player
    private val onItemClick: (Triple<String, String, Player>) -> Unit
) : RecyclerView.Adapter<SearchPlayerAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvPlayerName)
        val tvTeam = view.findViewById<TextView>(R.id.tvPlayerTeam)

        init {
            view.setOnClickListener {
                onItemClick(players[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_search_player, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (teamName, _, player) = players[position]
        holder.tvName.text = player.name
        holder.tvTeam.text = teamName
    }

    override fun getItemCount(): Int = players.size
}

