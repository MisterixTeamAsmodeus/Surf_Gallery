package com.misterixteam.asmodeus.surfgallery.ui.activity.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
import com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite.FavoriteFragment
import com.misterixteam.asmodeus.surfgallery.ui.fragment.home.HomeFragment
import com.misterixteam.asmodeus.surfgallery.ui.fragment.profile.ProfileFragment

class MainViewModel(private val view: MainActivityContract.View) : MainActivityContract.ViewModel {

    private var navigationBarIndex = mutableStateOf(0)
    private val token = AppPreference().getUserToken(view.getContext())
    private var fragment: MutableState<BaseFragment> =
        mutableStateOf(
            HomeFragment(
                view.getContext(),
                token
            )
        )

    override fun getNavigationBarIndex(): MutableState<Int> {
        return navigationBarIndex
    }

    override fun changeFragment(index: Int) {
        navigationBarIndex.value = index
        fragment.value = when (index) {
            0 -> HomeFragment(
                view.getContext(),
                token
            )
            1 -> FavoriteFragment(view.getContext())
            2 -> ProfileFragment(view.getContext(), token)
            else -> {
                HomeFragment(
                    view.getContext(),
                    token
                )
            }
        }
    }

    override fun getFragment(): MutableState<BaseFragment> {
        return fragment
    }
}