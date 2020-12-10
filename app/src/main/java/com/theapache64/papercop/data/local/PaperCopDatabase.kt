package com.theapache64.papercop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theapache64.papercop.data.local.entities.players.PlayersDao
import com.theapache64.papercop.data.local.entities.players.PlayerEntity

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 11:39
 */
@Database(
    entities = [PlayerEntity::class],
    version = 1
)
abstract class PaperCopDatabase : RoomDatabase() {
    abstract fun playersDao(): PlayersDao
}