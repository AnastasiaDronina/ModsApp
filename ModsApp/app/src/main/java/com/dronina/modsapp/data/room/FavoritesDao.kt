package com.dronina.modsapp.data.room

import androidx.room.*
import com.dronina.modsapp.data.entities.Favorite
import com.dronina.modsapp.utils.FAVORITES

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Update
    suspend fun update(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM $FAVORITES")
    suspend fun getAll(): List<Favorite>

}
