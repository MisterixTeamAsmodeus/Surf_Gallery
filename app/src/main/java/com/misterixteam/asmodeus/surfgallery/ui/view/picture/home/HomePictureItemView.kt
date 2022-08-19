package com.misterixteam.asmodeus.surfgallery.ui.view.picture.home

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.misterixteam.asmodeus.surfgallery.ui.fragment.home.HomeFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemView

class HomePictureItemView(
    context: Context,
) :
    PictureItemView(context),
    HomePictureItemContract.View,
    HomeFragmentContract.Picture.View {

    override fun initViewModel(): PictureItemContract.ViewModel {
        return HomePictureViewModel(this)
    }

    override fun getViewModel(): HomeFragmentContract.Picture.ViewModel {
        return viewModel as HomeFragmentContract.Picture.ViewModel
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun DrawGrid() {
        val pictures = remember {
            viewModel.getPictureItemState()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(
                items = pictures,
                key = { it.picture.id }
            ) {
                DrawItem(
                    item = it,
                    modifier = Modifier.animateItemPlacement(),
                    imageModifier = Modifier.height(222.dp),
                )
            }
        }
    }
}