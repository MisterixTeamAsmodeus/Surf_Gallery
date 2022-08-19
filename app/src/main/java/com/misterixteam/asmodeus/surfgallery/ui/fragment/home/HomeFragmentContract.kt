package com.misterixteam.asmodeus.surfgallery.ui.fragment.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.google.accompanist.swiperefresh.SwipeRefreshState

interface HomeFragmentContract {
    interface View {
        fun openActivity(clazz: Class<*>)
        fun openNewActivity(clazz: Class<*>)
    }

    interface ViewModel {
        fun updateData()
        fun openSearchActivity()
        fun getLoadState(): SwipeRefreshState
        fun getErrorState(): MutableState<Boolean>
        fun isPictureEmptyState(): MutableState<Boolean>
    }

    interface Data {
        fun getUserToken(context: Context): String?
    }

    interface Picture {
        interface View {
            fun getViewModel(): ViewModel

            @Composable
            fun DrawGrid()
        }

        interface ViewModel {
            fun updatePicture(token: String, onSuccessful: () -> Unit, onError: () -> Unit)
            fun isPictureEmptyState(): MutableState<Boolean>
        }
    }
}