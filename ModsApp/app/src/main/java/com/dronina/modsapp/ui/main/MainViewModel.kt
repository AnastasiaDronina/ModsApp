package com.dronina.modsapp.ui.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dronina.modsapp.data.entities.Favorite
import com.dronina.modsapp.data.entities.FavoriteMod
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.data.repository.Repository
import com.dronina.modsapp.utils.createFavoriteMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val activity: Activity) : ViewModel() {
    private val repo = Repository(activity)
    private val allMods = MutableLiveData<List<Mod>>()
    val mods = MutableLiveData<List<FavoriteMod>>()
    val favoriteMods = MutableLiveData<List<FavoriteMod>>()

    fun onCreate() {
        allMods.value = repo.getAllMods()
        viewModelScope.launch {
            var favorites: List<Favorite>? = null
            val modsNew: ArrayList<FavoriteMod> = ArrayList()
            val favoriteModsNew: ArrayList<FavoriteMod> = ArrayList()
            withContext(Dispatchers.IO) {
                repo.getFavorites()?.let {
                    favorites = it
                }
            }
            favorites?.let { favorites ->
                allMods.value?.forEach { mod ->
                    val containes =
                        (favorites.filter { fav -> fav.toString() == mod.file }).isNotEmpty()
                    val favoriteMod = mod.createFavoriteMode(containes)
                    modsNew.add(favoriteMod)
                    if (favoriteMod.favorite) {
                        favoriteModsNew.add(favoriteMod)
                    }
                }
            }
            mods.value = modsNew
            favoriteMods.value = favoriteModsNew
        }
    }
}