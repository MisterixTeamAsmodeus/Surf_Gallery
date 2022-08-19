package com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite

import androidx.compose.runtime.MutableState

class FavoriteViewModel(
    private val pictureViewModel: FavoriteFragmentContract.Picture.ViewModel
) :
    FavoriteFragmentContract.ViewModel {

    init {
        pictureViewModel.updatePicture()
    }

    override fun isPictureEmptyState(): MutableState<Boolean> {
        return pictureViewModel.isPictureEmptyState()
    }
}