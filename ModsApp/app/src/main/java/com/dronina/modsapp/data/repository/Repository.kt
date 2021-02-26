package com.dronina.modsapp.data.repository

import android.app.Activity
import android.util.Log
import com.dronina.modsapp.data.entities.Favorite
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.data.room.FavoritesDao
import com.dronina.modsapp.data.room.ModsDatabase
import com.dronina.modsapp.utils.TAG
import com.dronina.modsapp.utils.getJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Repository(private val activity: Activity) {
    private var dao: FavoritesDao? = null

    init {
        dao = ModsDatabase.getDatabase(activity)?.favoritesDao()
    }

    fun getAllMods(): List<Mod> {
        val jsonList = activity.getJson()
        val gson = Gson()
        val arrayModsType = object : TypeToken<Array<Mod>>() {}.type
        return gson.fromJson<Array<Mod>?>(jsonList, arrayModsType).toList()
    }

    suspend fun getFavorites(): List<Favorite>? {
        val all = dao?.getAll()
        return all
    }

    suspend fun addOrRemoveFavorite(mod: Mod?) {
        mod?.let {
            dao?.getAll()?.let { all ->
                val exists = (all.filter { fav -> fav.toString() == mod.file }).isNotEmpty()
                if (exists) {
                    dao?.delete(Favorite(mod.file))
                } else dao?.insert(Favorite(mod.file))
            } ?: run {
                dao?.insert(Favorite(mod.file))
            }
        }
    }
}