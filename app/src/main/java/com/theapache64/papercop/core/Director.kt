package com.theapache64.papercop.core

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 18:17
 */
object Director {


    private const val SOLDIER_NAME = "Soldier"
    private const val SOLDIER_POINT = 300

    private val CHARS_TABLE = mapOf(

        // Mandatory chars
        "King 👑" to 1000,
        "Police 👮" to 700,
        "Thief 👺" to 0,

        // Optional chars
        "Queen" to 900,
        "Minister" to 800,

        // Special char
        SOLDIER_NAME to SOLDIER_POINT
    ).toList()


    fun provideCharacters(players: List<String>): Map<String, String> {

        val charsTable = if (players.size > CHARS_TABLE.size) {
            // Here number of players are greater than available chars. so we need to fill up
            // the missing space with soldiers
            CHARS_TABLE
                .toMutableList()
                .apply {
                    // fill missing chars with soldier
                    repeat(players.size - CHARS_TABLE.size) {
                        add(Pair(SOLDIER_NAME, SOLDIER_POINT))
                    }
                }
        } else {
            CHARS_TABLE
        }

        return players
            .shuffled() // shuffling player position
            .mapIndexed { index, playerName ->
                val charName = charsTable[index].first
                Pair(playerName, charName) // getting random character
            }
            .shuffled() // shuffling order
            .toMap()
    }
}