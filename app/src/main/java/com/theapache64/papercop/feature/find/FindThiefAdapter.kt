package com.theapache64.papercop.feature.find

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.databinding.ItemFindThiefBinding
import com.theapache64.papercop.model.Role

/**
 * Created by theapache64 : Dec 12 Sat,2020 @ 13:59
 */
class FindThiefAdapter(
    private val context: Context,
    private val rolesMap: HashMap<PlayerEntity, Role>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<FindThiefAdapter.ViewHolder>() {

    class State(
        val shouldRevealRole: Boolean,
        val shouldSwitchPoliceThiefPoints: Boolean
    )

    val keyList = rolesMap.keys.toList()
    var state = State(false, false)

    private val inflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFindThiefBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = keyList[position]
        val role = rolesMap[player]!!

        val point = if (state.shouldSwitchPoliceThiefPoints) {
            when (role.name) {

                Director.ROLE_NAME_THIEF -> {
                    Director.ROLE_POINT_POLICE
                }
                Director.ROLE_NAME_POLICE -> {
                    Director.ROLE_POINT_THIEF
                }
                else -> {
                    role.point
                }
            }
        } else {
            role.point
        }


        holder.binding.player = player
        holder.binding.role = role
        holder.binding.point = point
        holder.binding.shouldRevealPoint = state.shouldRevealRole
        holder.binding.shouldRevealRole =
            role.name == Director.ROLE_NAME_POLICE || state.shouldRevealRole
    }

    override fun getItemCount(): Int = rolesMap.size

    inner class ViewHolder(
        val binding: ItemFindThiefBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (state.shouldRevealRole.not()) {
                    // only if not revealed before
                    onItemClicked(layoutPosition)
                }
            }
        }
    }
}