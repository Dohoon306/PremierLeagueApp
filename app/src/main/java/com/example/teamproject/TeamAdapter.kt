package com.example.teamproject


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter(
    private val teams: List<Team>,
    private val onItemClick: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamVH>() {

    inner class TeamVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRank: TextView = itemView.findViewById(R.id.tvRank)
        val imgLogo: ImageView = itemView.findViewById(R.id.imgLogo)
        val tvName: TextView = itemView.findViewById(R.id.tvTeamName)
        val tvPts: TextView = itemView.findViewById(R.id.tvPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_team, parent, false)
        return TeamVH(v)
    }

    override fun onBindViewHolder(holder: TeamVH, position: Int) {
        val team = teams[position]

        holder.tvRank.text = team.rank.toString()
        holder.tvName.text = team.name
        holder.tvPts.text = team.points.toString()

        val resId = holder.itemView.context.resources.getIdentifier(
            team.logo, "drawable", holder.itemView.context.packageName
        )
        holder.imgLogo.setImageResource(resId)

        holder.itemView.setOnClickListener {
            onItemClick(team)
        }
    }

    override fun getItemCount() = teams.size
}
