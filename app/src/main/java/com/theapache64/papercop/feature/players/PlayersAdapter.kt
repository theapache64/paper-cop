package com.theapache64.papercop.feature.players

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 10:49
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.databinding.ItemPlayerBinding

class PlayersAdapter(
    context: Context,
    private val players: List<PlayerEntity>
) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.binding.player = player
    }

    inner class ViewHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)
}