package com.dronina.modsapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dronina.modsapp.data.entities.Favorite
import com.dronina.modsapp.utils.DATABASE


@Database(entities = [Favorite::class], version = 1)
abstract class ModsDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        var INSTANCE: ModsDatabase? = null

        fun getDatabase(context: Context): ModsDatabase? {
            if (INSTANCE == null) {
                synchronized(ModsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ModsDatabase::class.java,
                        DATABASE
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}