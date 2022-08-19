package com.misterixteam.asmodeus.surfgallery.ui.activity.main

import android.content.Context
import androidx.compose.runtime.MutableState
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment

interface MainActivityContract {
    interface View {
        fun getContext(): Context
    }

    interface ViewModel {
        fun getNavigationBarIndex(): MutableState<Int>
        fun changeFragment(index: Int)
        fun getFragment(): MutableState<BaseFragment>
    }

    interface Data {
        fun getUserToken(context: Context): String?
    }
}