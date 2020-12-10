package com.theapache64.papercop.data.repo

import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.data.local.entities.players.PlayersDao
import javax.inject.Inject

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 22:33
 */
class PlayersRepo @Inject constructor(
    private val playersDao: PlayersDao
) {
    /**
     * To remove all players from table
     */
    suspend fun removeAllPlayers() = playersDao.deleteAll()


    /**
     * To add players
     */
    suspend fun addPlayers(names: List<String>) {
        val players = names.map { name -> PlayerEntity(name, 0) }
        playersDao.addAll(players)
    }

}