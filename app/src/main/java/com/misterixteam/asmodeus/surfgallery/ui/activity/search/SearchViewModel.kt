package com.misterixteam.asmodeus.surfgallery.ui.activity.search

import androidx.compose.runtime.MutableState
import com.misterixteam.asmodeus.surfgallery.data.AppPreference

class SearchViewModel(
    view: SearchActivityContract.View,
    private val pictureViewModel: SearchActivityContract.Picture.ViewModel
) : SearchActivityContract.ViewModel {

    init {
        AppPreference().getUserToken(view.getContext())?.let {
            pictureViewModel.loadData(it)
        }
    }


    override fun isPictureEmptyState(): MutableState<Boolean> {
        return pictureViewModel.isPictureEmptyState()
    }

    override fun textChange(text: String) {
        pictureViewModel.setSearchText(text)
    }
}