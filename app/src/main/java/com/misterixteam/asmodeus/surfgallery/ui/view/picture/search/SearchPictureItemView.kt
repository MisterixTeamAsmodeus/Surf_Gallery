package com.misterixteam.asmodeus.surfgallery.ui.view.picture.search

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.misterixteam.asmodeus.surfgallery.ui.activity.search.SearchActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemView

class SearchPictureItemView(
    context: Context
) :
    PictureItemView(context),
    SearchPictureItemContract.View,
    SearchActivityContract.Picture.View {

    override fun initViewModel(): PictureItemContract.ViewModel {
        return SearchPictureViewModel(this)
    }

    override fun getViewModel(): SearchActivityContract.Picture.ViewModel {
        return viewModel as SearchActivityContract.Picture.ViewModel
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun DrawGrid() {
        val pictures = remember {
            viewModel.getPictureItemState()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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
                    imageModifier = Modifier.height(222.dp)
                )
            }
        }
    }
}