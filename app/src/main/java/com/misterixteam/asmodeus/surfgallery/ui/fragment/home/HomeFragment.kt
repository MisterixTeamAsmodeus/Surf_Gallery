package com.misterixteam.asmodeus.surfgallery.ui.fragment.home

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawButton
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawHintMessage
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawTitle
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.home.HomePictureItemView

class HomeFragment(
    context: Context,
    userToken: String?
) :
    BaseFragment,
    HomeFragmentContract.View {

    private val context = context
    private val pictureItemView: HomeFragmentContract.Picture.View =
        HomePictureItemView(context)
    private val viewModel: HomeFragmentContract.ViewModel =
        HomeViewModel(
            this,
            pictureItemView,
            userToken
        )

    init {
        viewModel.updateData()
    }

    @Composable
    override fun DrawFragment() {
        val errorState = remember {
            viewModel.getErrorState()
        }

        Column {
            DrawTitle(
                text = "Галерея",
                helperIconResource = R.drawable.ic_search,
                onIconClick = {
                    viewModel.openSearchActivity()
                })
            if (!errorState.value)
                DrawInfo()
        }

        if (errorState.value) {
            DrawHintMessage(
                text = "Не удалось загрузить ленту\nОбновите экран или попробуйте позже",
                icon = R.drawable.ic_bad
            )
            Box(modifier = Modifier.fillMaxHeight()) {
                DrawButton(
                    onClick = { viewModel.updateData() },
                    text = "Обновить",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        }

    }

    @Composable
    private fun DrawInfo() {
        val loadState = remember {
            viewModel.getLoadState()
        }
        val isPictureEmptyState = remember {
            viewModel.isPictureEmptyState()
        }

        if (isPictureEmptyState.value) {
            if (loadState.isRefreshing) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        } else {
            SwipeRefresh(
                state = loadState,
                onRefresh = {
                    viewModel.updateData()
                },
            ) {
                pictureItemView.DrawGrid()
            }
        }
    }


    override fun openActivity(clazz: Class<*>) {
        (context as ComponentActivity).startActivity(Intent(context, clazz))
    }

    override fun openNewActivity(clazz: Class<*>) {
        openActivity(clazz)
        (context as ComponentActivity).finish()
    }

}