package com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

interface FavoriteFragmentContract {
    interface View

    interface ViewModel {
        fun isPictureEmptyState(): MutableState<Boolean>
    }

    interface Data

    interface Picture {
        interface View {
            fun getViewModel(): ViewModel

            @Composable
            fun DrawPictures()
        }

        interface ViewModel {
            fun isPictureEmptyState(): MutableState<Boolean>
            fun updatePicture()
        }
    }
}