package com.misterixteam.asmodeus.surfgallery.ui.view.picture.favorite

import com.misterixteam.asmodeus.surfgallery.model.picture.PictureItem
import com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite.FavoriteFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureViewModel

class FavoritePictureItemViewModel(
    view: FavoritePictureItemContract.View
) :
    PictureViewModel(view),
    FavoritePictureItemContract.ViewModel,
    FavoriteFragmentContract.Picture.ViewModel {

    override fun onFavoriteClick(item: PictureItem) {
        super.onFavoriteClick(item)
        updatePicture()
    }

    override fun updatePicture() {
        val array = getFavoriteArray()
        if (array.isEmpty())
            clearPicture()
        else
            replacePicture(array)
    }
}