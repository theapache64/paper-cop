package com.theapache64.papercop.core

import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.model.Role

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 18:17
 */
object Director {


    private val warrior = Role(
        "Warrior",
        300,
        "🗡️"
    )

    private val availableRoles = listOf(
        Role(
            "King",
            1000,
            "👑"
        ),

        Role(
            "Police",
            700,
            "👮"
        ),

        Role(
            "Thief",
            0,
            "👺"
        ),

        Role(
            "Queen",
            900,
            "👸"
        ),

        Role(
            "Minister",
            800,
            "👨"
        ),

        warrior
    )

    fun provideRoles(players: List<PlayerEntity>): Map<PlayerEntity, Role> {

        val roles = if (players.size > availableRoles.size) {
            // Here number of players are greater than available chars. so we need to fill up
            // the missing space with soldiers
            availableRoles
                .toMutableList()
                .apply {
                    // fill missing chars with soldier
                    val noOfRolesNeeded = players.size - availableRoles.size
                    repeat(noOfRolesNeeded) {
                        add(warrior)
                    }
                }
        } else {
            availableRoles
        }

        return players
            .shuffled() // shuffling player position
            .mapIndexed { index, player ->
                val role = roles[index]
                Pair(player, role) // getting random character
            }
            .shuffled() // shuffling order
            .toMap()
    }
}