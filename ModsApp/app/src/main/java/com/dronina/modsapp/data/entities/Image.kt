package com.dronina.modsapp.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Image(
    val image: String
): Parcelable {
    override fun toString(): String {
        return image
    }
}