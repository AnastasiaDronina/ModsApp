package com.dronina.modsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dronina.modsapp.utils.FAVORITES

@Entity(tableName = FAVORITES)
class Favorite(
    @PrimaryKey(autoGenerate = false)
    val file: String
    ) {
    override fun toString(): String {
        return file
    }
}