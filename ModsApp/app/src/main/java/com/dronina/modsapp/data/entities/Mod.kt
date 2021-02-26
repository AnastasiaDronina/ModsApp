package com.dronina.modsapp.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Mod(
    val images: List<Image>,
    val file: String,
    @SerializedName("title_def")
    val titleDef: String,
    @SerializedName("title_ru")
    val titleRu: String,
    @SerializedName("desc_def")
    val descDef: String,
    @SerializedName("desc_ru")
    val descRu: String
): Parcelable {
    override fun toString(): String {
        return titleDef + "\n" + descDef + "\n" + file + "\n" + images + "\n\n"
    }
}