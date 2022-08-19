package com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.model.picture.PictureItem
import java.text.SimpleDateFormat

abstract class PictureItemView(context: Context) : PictureItemContract.View {

    private val context: Context = context

    protected val viewModel: PictureItemContract.ViewModel = initViewModel()

    abstract fun initViewModel(): PictureItemContract.ViewModel

    override fun openActivity(intent: Intent) {
        (context as ComponentActivity).startActivity(intent)
    }

    override fun getContext(): Context {
        return context
    }

    @Composable
    protected fun DrawItem(
        item: PictureItem,
        modifier: Modifier,
        imageModifier: Modifier,
        showContent: Boolean = false
    ) {
        val openDialog = remember { mutableStateOf(false) }

        if (openDialog.value) {
            ShowDialog(openDialog) {
                viewModel.onFavoriteClick(item)
            }
        }

        Column(modifier = modifier
            .padding(8.dp)
            .clickable {
                viewModel.onItemClick(item)
            }) {
            Box(modifier = Modifier.fillMaxWidth()) {
                item.image?.let {
                    Image(
                        bitmap = it,
                        contentDescription = item.picture.photoUrl,
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }
                IconButton(
                    onClick = {
                        if (item.isFavorite) {
                            openDialog.value = true
                        } else {
                            viewModel.onFavoriteClick(item)
                        }
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (item.isFavorite)
                                R.drawable.ic_favorite
                            else
                                R.drawable.ic_favorite_diseble
                        ),
                        contentDescription = "favorite"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = item.picture.title,
                    style = TextStyle(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                if (showContent) {
                    Text(
                        text = SimpleDateFormat("dd.MM.yyyy").format(item.picture.publicationDate),
                        style = TextStyle(color = Color.Gray, fontSize = 10.sp),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
            if (showContent) {
                Text(
                    text = item.picture.content,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    style = TextStyle(color = Color.Black, fontSize = 12.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    private fun ShowDialog(openDialog: MutableState<Boolean>, onSuccessful: () -> Unit) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Вы точно хотите удалить из избранного?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onSuccessful()
                    }
                ) {
                    Text("да, точно".uppercase(), style = TextStyle(Color.Black))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("нет".uppercase(), style = TextStyle(Color.Black))
                }
            }
        )
    }
}