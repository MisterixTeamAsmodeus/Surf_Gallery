package com.misterixteam.asmodeus.surfgallery.ui.activity.preview

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap

interface PreviewActivityContract {
    interface View

    interface ViewModel {
        fun getImage(): MutableState<ImageBitmap?>
        fun loadImage()
        fun setImageUrl(photoUrl: String)
    }
}