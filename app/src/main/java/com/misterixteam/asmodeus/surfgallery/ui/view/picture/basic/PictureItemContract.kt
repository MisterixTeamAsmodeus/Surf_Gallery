package com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.model.picture.PictureItem

interface PictureItemContract {

    interface View {
        fun openActivity(intent: Intent)
        fun getContext(): Context
    }

    interface ViewModel {
        fun isPictureEmptyState(): MutableState<Boolean>
        fun getPictureItemState(): SnapshotStateList<PictureItem>
        fun onItemClick(item: PictureItem)
        fun onFavoriteClick(item: PictureItem)
    }

    interface Data {
        fun savePicture(picture: Picture)
        fun deletePicture(picture: Picture)
        fun loadPictures(): Array<Picture>
    }

    interface Server {
        fun getPicture(
            token: String,
            onSuccessful: (Array<Picture>) -> Unit,
            onError: (Int) -> Unit
        )
    }
}