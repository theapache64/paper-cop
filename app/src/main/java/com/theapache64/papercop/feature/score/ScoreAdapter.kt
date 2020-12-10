package com.theapache64.papercop.feature.score

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 10:49
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.papercop.databinding.ItemScoreBinding

class ScoresAdapter(
    context: Context,
    private val scores: List<Score>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScoreBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = scores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val score = scores[position]
        holder.binding.score = score
    }

    inner class ViewHolder(val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}