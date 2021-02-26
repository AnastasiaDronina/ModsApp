package com.dronina.modsapp.ui.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.data.repository.Repository
import com.dronina.modsapp.utils.AssetCopier
import com.dronina.modsapp.utils.MOD_BUNDLE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException


class DetailsViewModel(private var activity: Activity) : ViewModel() {
    private val repo = Repository(activity)
    var view: DetailsFragment? = null
    val currentMod = MutableLiveData<Mod>()
    val isDownloaded = MutableLiveData<Boolean>()

    fun onViewCreated(bundle: Bundle?) {
        if (bundle != null) {
            currentMod.value = bundle.getParcelable(MOD_BUNDLE)
            isDownloaded.value = false
        }
    }

    fun downloadClicked() {
        viewModelScope.launch {
            delay(1000)
            isDownloaded.value = true
        }
    }

    fun installClicked() {
        currentMod.value?.let { mod ->
            importFile(mod.file)
        }
    }

    fun copyAssets() {
        try {
            val destDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            destDir.mkdirs()
            val count = AssetCopier(activity)
                .withFileScanning()
                .copy("files", destDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun importFile(filename: String) {
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/$filename"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = "file/*"
        intent.data = Uri.parse("minecraft://?import=${filePath}")
        view?.startActivity(intent)
    }
}