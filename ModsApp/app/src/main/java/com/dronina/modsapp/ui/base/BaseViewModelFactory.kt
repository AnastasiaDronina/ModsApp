package com.dronina.modsapp.ui.base

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dronina.modsapp.ui.details.DetailsViewModel
import com.dronina.modsapp.ui.favorites.FavoriteViewModel
import com.dronina.modsapp.ui.main.MainViewModel
import com.dronina.modsapp.ui.mods.ModsViewModel
import com.dronina.modsapp.utils.UNKNOWN_VIEW_MODEL

class BaseViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(activity) as T
            modelClass.isAssignableFrom(ModsViewModel::class.java) ->
                ModsViewModel(activity) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(activity) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) ->
                DetailsViewModel(activity) as T
            else -> throw IllegalArgumentException(UNKNOWN_VIEW_MODEL)
        }
    }
}