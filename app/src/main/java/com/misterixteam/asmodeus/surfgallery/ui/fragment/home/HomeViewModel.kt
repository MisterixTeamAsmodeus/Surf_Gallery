package com.misterixteam.asmodeus.surfgallery.ui.fragment.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.misterixteam.asmodeus.surfgallery.ui.activity.login.LoginActivity
import com.misterixteam.asmodeus.surfgallery.ui.activity.search.SearchActivity

class HomeViewModel(
    view: HomeFragmentContract.View,
    pictureItemView: HomeFragmentContract.Picture.View,
    userToken: String?
) : HomeFragmentContract.ViewModel {

    private val view: HomeFragmentContract.View = view
    private val pictureViewModel: HomeFragmentContract.Picture.ViewModel =
        pictureItemView.getViewModel()
    private val token: String? = userToken

    private val loadState = SwipeRefreshState(false)
    private val errorState = mutableStateOf(false)

    override fun getErrorState(): MutableState<Boolean> {
        return errorState
    }

    override fun isPictureEmptyState(): MutableState<Boolean> {
        return pictureViewModel.isPictureEmptyState()
    }

    override fun getLoadState(): SwipeRefreshState {
        return loadState
    }

    override fun updateData() {
        if (token.isNullOrEmpty()) {
            view.openNewActivity(LoginActivity::class.java)
        } else {
            errorState.value = false
            loadState.isRefreshing = true
            pictureViewModel.updatePicture(
                token = token,
                onSuccessful = {
                    loadState.isRefreshing = false
                },
                onError = {
                    if (it == 404)
                        view.openNewActivity(LoginActivity::class.java)
                    loadState.isRefreshing = false
                    errorState.value = true
                }
            )
        }
    }

    override fun openSearchActivity() {
        view.openActivity(SearchActivity::class.java)
    }

}