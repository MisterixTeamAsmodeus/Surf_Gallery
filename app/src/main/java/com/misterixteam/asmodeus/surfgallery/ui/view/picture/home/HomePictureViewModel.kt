package com.misterixteam.asmodeus.surfgallery.ui.view.picture.home

import com.misterixteam.asmodeus.surfgallery.ui.fragment.home.HomeFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureViewModel

class HomePictureViewModel(
    view: HomePictureItemContract.View,
) :
    PictureViewModel(view),
    HomePictureItemContract.ViewModel,
    HomeFragmentContract.Picture.ViewModel {

    override fun updatePicture(token: String, onSuccessful: () -> Unit, onError: (Int) -> Unit) {
        loadImageData(
            token = token,
            onSuccessful = {
                if (it.isEmpty()) {
                    clearPicture()
                    onError(1)
                } else {
                    replacePicture(it)
                    onSuccessful()
                }
            },
            onError = {
                clearPicture()
                onError(it)
            }
        )
    }
}