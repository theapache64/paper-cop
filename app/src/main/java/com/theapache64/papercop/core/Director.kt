package com.theapache64.papercop.core

import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.model.Role

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 18:17
 */
object Director {

    const val ROLE_NAME_THIEF = "Thief"
    const val ROLE_NAME_POLICE = "Police"
    const val ROLE_POINT_POLICE = 700
    const val ROLE_POINT_THIEF = 0

    private val soldier = Role(
        "Soldier",
        300,
        "🗡️"
    )

    val availableRoles = listOf(
        Role("King", 1000, "👑"),
        Role("Police", ROLE_POINT_POLICE, "👮"),
        Role(ROLE_NAME_THIEF, ROLE_POINT_THIEF, "👺"),
        Role("Queen", 900, "👸"),
        Role("Minister", 800, "👨"),
        soldier
    )

    fun provideRoles(players: List<PlayerEntity>): HashMap<PlayerEntity, Role> {

        val roles = if (players.size > availableRoles.size) {
            // Here number of players are greater than available chars. so we need to fill up
            // the missing space with soldiers
            availableRoles
                .toMutableList()
                .apply {
                    // fill missing chars with soldier
                    val noOfRolesNeeded = players.size - availableRoles.size
                    repeat(noOfRolesNeeded) {
                        add(soldier)
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
            .toMap() as HashMap<PlayerEntity, Role>
    }
}