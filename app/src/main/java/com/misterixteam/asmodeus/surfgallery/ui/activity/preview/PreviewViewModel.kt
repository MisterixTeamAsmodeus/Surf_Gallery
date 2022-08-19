package com.misterixteam.asmodeus.surfgallery.ui.activity.preview

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.misterixteam.asmodeus.surfgallery.data.BitmapDownloader

class PreviewViewModel(context: Context) : PreviewActivityContract.ViewModel {

    private var photoUrl = ""
    private val image: MutableState<ImageBitmap?> = mutableStateOf(null)
    private val bitmapDownloader = BitmapDownloader(context)

    override fun getImage(): MutableState<ImageBitmap?> {
        return image
    }

    override fun loadImage() {
        bitmapDownloader.getBitmap(photoUrl) {
            image.value = it.asImageBitmap()
        }
    }

    override fun setImageUrl(photoUrl: String) {
        this.photoUrl = photoUrl
    }
}