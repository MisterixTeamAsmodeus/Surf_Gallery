package com.misterixteam.asmodeus.surfgallery.ui.fragment.home

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
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
            DrawTitle()
            if (errorState.value)
                DrawError()
            else
                DrawInfo()
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

    @Composable
    private fun DrawError() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bad),
                    contentDescription = "empty",
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Не удалось загрузить ленту\n" +
                            "Обновите экран или попробуйте позже",
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.updateData() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Обновить",
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun DrawTitle() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Галерея",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp),
                style = TextStyle(fontSize = 24.sp)
            )
            IconButton(
                onClick = { viewModel.openSearchActivity() },
                modifier = Modifier
                    .padding(top = 8.dp, end = 16.dp)
                    .align(Alignment.BottomEnd),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search"
                )
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