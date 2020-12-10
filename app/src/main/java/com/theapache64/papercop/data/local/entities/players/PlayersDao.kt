package com.theapache64.papercop.data.local.entities.players

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 13:09
 */
@Dao
interface PlayersDao {
    @Query("DELETE FROM players")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(players: List<PlayerEntity>)

    @Query("SELECT * FROM players ORDER BY total_score DESC")
    fun getAll(): Flow<List<PlayerEntity>>
}