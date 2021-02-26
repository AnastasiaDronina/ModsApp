package com.dronina.modsapp.data.entities

class FavoriteMod(
    images: List<Image>,
    file: String,
    titleDef: String,
    titleRu: String,
    descDef: String,
    descRu: String,
    var favorite: Boolean = false
) : Mod(images, file, titleDef, titleRu, descDef, descRu) {
}