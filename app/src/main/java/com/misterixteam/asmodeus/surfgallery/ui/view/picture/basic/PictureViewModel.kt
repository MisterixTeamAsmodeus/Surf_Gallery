package com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asImageBitmap
import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.data.BitmapDownloader
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabase
import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.model.picture.PictureItem
import com.misterixteam.asmodeus.surfgallery.server.Server
import com.misterixteam.asmodeus.surfgallery.ui.activity.preview.PreviewActivity

open class PictureViewModel(
    private val view: PictureItemContract.View,
) :
    PictureItemContract.ViewModel {

    private val pictureItemState = mutableStateListOf<PictureItem>()
    private val pictureEmptyState = mutableStateOf(true)
    private val server: PictureItemContract.Server = Server()
    private val bitmapDownloader = BitmapDownloader(view.getContext())
    private val database: PictureItemContract.Data =
        FavoritePictureDatabase(
            view.getContext(),
            AppPreference().getUserInfo(view.getContext()).id
        )

    override fun getPictureItemState(): SnapshotStateList<PictureItem> {
        return pictureItemState
    }

    override fun isPictureEmptyState(): MutableState<Boolean> {
        return pictureEmptyState
    }

    override fun onItemClick(item: PictureItem) {
        val intent = Intent(view.getContext(), PreviewActivity::class.java)
        intent.putExtra(PreviewActivity.IMAGE_DATA, item.picture.toJson())
        view.openActivity(intent)
    }

    override fun onFavoriteClick(item: PictureItem) {
        val index = pictureItemState.indexOf(item)
        if (pictureItemState[index].isFavorite) {
            database.deletePicture(pictureItemState[index].picture)
        } else {
            database.savePicture(pictureItemState[index].picture)
        }
        pictureItemState[index] =
            pictureItemState[index].copy(isFavorite = !pictureItemState[index].isFavorite)
    }

    protected fun loadImageData(
        token: String,
        onSuccessful: (Array<Picture>) -> Unit,
        onError: (Int) -> Unit = {}
    ) {
        server.getPicture(
            token = token,
            onSuccessful = onSuccessful,
            onError = {
                pictureItemState.clear()
                onError(it)
            }
        )
    }

    protected fun getFavoriteArray(): Array<Picture> {
        return database.loadPictures()
    }

    protected fun replacePicture(array: Array<Picture>) {
        val arrayFavorite = getFavoriteArray()
        clearPicture()
        pictureEmptyState.value = false
        array.forEach { picture ->
            bitmapDownloader.getBitmap(picture.photoUrl) {
                pictureItemState.add(
                    PictureItem(
                        picture,
                        it.asImageBitmap(),
                        arrayFavorite.contains(picture)
                    )
                )
            }
        }
    }

    protected fun clearPicture() {
        pictureEmptyState.value = true
        pictureItemState.clear()
    }
}