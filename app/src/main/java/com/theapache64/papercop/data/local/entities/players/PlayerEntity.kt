package com.theapache64.papercop.data.local.entities.players

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 11:42
 */
@Entity(
    tableName = "players",
    indices = [Index("name", unique = true)]
)
class PlayerEntity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "total_score")
    val totalScore: Int
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}