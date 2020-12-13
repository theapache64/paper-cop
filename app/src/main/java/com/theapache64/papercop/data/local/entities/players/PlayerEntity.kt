package com.theapache64.papercop.data.local.entities.players

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 11:42
 */
@Entity(
    tableName = "players",
    indices = [Index("name", unique = true)]
)
@Parcelize
class PlayerEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "total_score")
    var totalScore: Int
) : Parcelable {

}