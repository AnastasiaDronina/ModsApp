package com.dronina.modsapp.ui.mods

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.data.repository.Repository
import com.dronina.modsapp.utils.MOD_BUNDLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModsViewModel(private var activity: Activity): ViewModel() {
    private val repo = Repository(activity)
    var view: ModsFragment? = null
    var scrollPosition = 0

    fun onItemClick(mod: Mod?) {
        mod?.let {
            val bundle = Bundle()
            bundle.putParcelable(MOD_BUNDLE, mod)
            view?.navigateDetailsPage(bundle)
        }
    }

    fun onFavoriteClick(mod: Mod?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addOrRemoveFavorite(mod)
            }
            view?.dataUpdated()
        }
    }
}