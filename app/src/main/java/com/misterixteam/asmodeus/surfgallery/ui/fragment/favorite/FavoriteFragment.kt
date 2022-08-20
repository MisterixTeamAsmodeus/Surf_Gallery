package com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawHintMessage
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawTitle
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.favorite.FavoritePictureItemView

class FavoriteFragment(context: Context) : BaseFragment,
    FavoriteFragmentContract.View {

    private val pictureView: FavoriteFragmentContract.Picture.View =
        FavoritePictureItemView(context)
    private val viewModel: FavoriteFragmentContract.ViewModel =
        FavoriteViewModel(
            pictureView.getViewModel()
        )

    @Composable
    override fun DrawFragment() {
        val isPictureEmptyState = remember {
            viewModel.isPictureEmptyState()
        }

        if (isPictureEmptyState.value) {
            DrawHintMessage(text = "В избранном пусто", icon = R.drawable.ic_bad)
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            DrawTitle(text = "Мои избранные")
            if (!isPictureEmptyState.value) {
                pictureView.DrawPictures()
            }
        }
    }
}