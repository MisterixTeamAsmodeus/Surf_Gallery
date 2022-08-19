package com.misterixteam.asmodeus.surfgallery.ui.view.picture.favorite

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite.FavoriteFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemView

class FavoritePictureItemView(
    context: Context,
) :
    PictureItemView(context),
    FavoritePictureItemContract.View,
    FavoriteFragmentContract.Picture.View {

    override fun initViewModel(): PictureItemContract.ViewModel {
        return FavoritePictureItemViewModel(this)
    }

    override fun getViewModel(): FavoriteFragmentContract.Picture.ViewModel {
        return viewModel as FavoriteFragmentContract.Picture.ViewModel
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun DrawPictures() {
        val pictures = remember {
            viewModel.getPictureItemState()
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            items(
                items = pictures,
                key = { it.picture.id }
            ) {
                DrawItem(
                    item = it,
                    modifier = Modifier.animateItemPlacement(),
                    imageModifier = Modifier
                        .height(358.dp)
                        .fillMaxWidth(),
                    showContent = true
                )
            }
        }
    }
}