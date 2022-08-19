package com.misterixteam.asmodeus.surfgallery.ui.fragment.favorite

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
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
                        text = "В избранном пусто",
                        style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Мои избранные",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                style = TextStyle(fontSize = 24.sp)
            )
            if (!isPictureEmptyState.value) {
                pictureView.DrawPictures()
            }
        }
    }
}