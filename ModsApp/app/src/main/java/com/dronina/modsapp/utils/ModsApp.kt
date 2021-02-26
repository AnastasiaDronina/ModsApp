package com.dronina.modsapp.utils

import android.app.Application
import android.content.Context
import java.util.*

class ModsApp : Application() {
    companion object {
        var instance: ModsApp? = null
            private set

        val context: Context?
            get() = instance

        var locale: Locale? = null
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        locale = resources?.configuration?.locale
    }
}
