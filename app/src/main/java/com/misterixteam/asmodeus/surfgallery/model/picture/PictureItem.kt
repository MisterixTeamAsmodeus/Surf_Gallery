package com.misterixteam.asmodeus.surfgallery.model.picture

import androidx.compose.ui.graphics.ImageBitmap

data class PictureItem(
    val picture: Picture,
    var image: ImageBitmap? = null,
    var isFavorite: Boolean = false
)