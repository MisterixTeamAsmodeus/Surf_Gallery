package com.misterixteam.asmodeus.surfgallery.ui.activity.search

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

interface SearchActivityContract {
    interface View {
        fun getContext(): Context
    }

    interface ViewModel {
        fun isPictureEmptyState(): MutableState<Boolean>
        fun textChange(text: String)
    }

    interface Picture {
        interface View {
            fun getViewModel(): ViewModel

            @Composable
            fun DrawGrid()
        }

        interface ViewModel {
            fun isPictureEmptyState(): MutableState<Boolean>
            fun loadData(token: String)
            fun setSearchText(text: String)
        }
    }
}