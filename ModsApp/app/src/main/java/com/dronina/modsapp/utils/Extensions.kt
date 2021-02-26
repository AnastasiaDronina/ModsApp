package com.dronina.modsapp.utils

import android.app.Activity
import android.content.Intent
import android.content.res.AssetManager
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dronina.modsapp.data.entities.FavoriteMod
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_mods.*
import java.io.*
import java.nio.charset.Charset
import java.util.*
import kotlin.system.exitProcess


fun Activity.setFullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Activity.navigateMainPage() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun Fragment.doNothingOnBackPressed() {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity(requireActivity())
            }
        }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}

fun Activity.printImage(name: String, imageView: ImageView?) {
    imageView?.let {
        Glide
            .with(this)
            .load("file:///android_asset/images/$name")
            .into(imageView)
    }
}

fun Activity.printImageForViewPager(name: String, imageView: ImageView?) {
    imageView?.let {
        Glide
            .with(this)
            .load("file:///android_asset/images/$name")
            .centerInside()
            .into(imageView)
    }
}

fun Activity.getJson(): String {
    var json = ""
    try {
        val inputStream = assets.open("Lucky_Block.json");
        val size = inputStream.available();
        val buffer = ByteArray(size)
        inputStream.read(buffer);
        inputStream.close();
        json = String(buffer, Charset.defaultCharset())
    } catch (e: Exception) {
    }
    return json
}

fun Mod.showTitle(): String {
    val longTitle = showLongTitle()
    return if (longTitle.length > 27) {
        longTitle.substring(0, 27) + "..."
    } else {
        longTitle
    }
}

fun Mod.showDescription(): String {
    val longDesc = showLongDescription()
    return if (longDesc.length > 64) {
        longDesc.substring(0, 63) + "..."
    } else {
        longDesc
    }
}

fun Mod.showLongTitle(): String {
    val locale = Locale.getDefault()
    return if (locale.isRussian()) {
        titleRu
    } else {
        titleDef
    }
}

fun Mod.showLongDescription(): String {
    val locale = Locale.getDefault()
    return if (locale.isRussian()) {
        descRu
    } else {
        descDef
    }
}

fun Mod.createFavoriteMode(favorite: Boolean): FavoriteMod {
    return FavoriteMod(
        images,
        file,
        titleDef,
        titleRu,
        descDef,
        descRu,
        favorite
    )
}

fun Locale.isRussian(): Boolean = this.toString().startsWith("ru_")