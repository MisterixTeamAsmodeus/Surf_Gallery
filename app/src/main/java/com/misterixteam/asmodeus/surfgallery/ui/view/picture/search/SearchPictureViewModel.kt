package com.misterixteam.asmodeus.surfgallery.ui.view.picture.search

import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.ui.activity.search.SearchActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureViewModel
import java.util.*

class SearchPictureViewModel(view: PictureItemContract.View) :
    PictureViewModel(view),
    SearchPictureItemContract.ViewModel,
    SearchActivityContract.Picture.ViewModel {

    private var searchText = ""
    private var pictureItems: Array<Picture>? = null

    override fun loadData(token: String) {
        loadImageData(
            token = token,
            onSuccessful = {
                pictureItems = it
            }
        )
    }

    override fun setSearchText(text: String) {
        searchText = text.lowercase(Locale.ROOT)
        if (searchText.isNotEmpty() && !pictureItems.isNullOrEmpty()) {
            val array = ArrayList<Picture>()
            pictureItems!!.forEach {
                if (it.title.lowercase(Locale.ROOT).contains(searchText))
                    array.add(it)
            }
            replacePicture(array.toArray(arrayOf()))
        } else {
            clearPicture()
        }
    }
}