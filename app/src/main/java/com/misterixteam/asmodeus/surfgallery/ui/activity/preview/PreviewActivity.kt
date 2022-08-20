package com.misterixteam.asmodeus.surfgallery.ui.activity.preview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.ui.theme.SurfGalleryTheme
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawExitButton
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawTitle
import java.text.SimpleDateFormat

class PreviewActivity : ComponentActivity() {

    companion object {
        const val IMAGE_DATA = "IMAGE_DATA"
    }

    private lateinit var viewModel: PreviewActivityContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getStringExtra(IMAGE_DATA)?.let { Picture.fromJson(it) }
        viewModel = PreviewViewModel(this)
        viewModel.setImageUrl(data!!.photoUrl)
        setContent {
            SurfGalleryTheme {
                PreviewScreen(data)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Composable
    private fun PreviewScreen(data: Picture) {
        val image = remember {
            viewModel.getImage()
        }
        Column(modifier = Modifier.padding(top = 8.dp)) {
            Row {
                DrawExitButton(onClick = { finish() })
                DrawTitle(text = "Галерея")
            }
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                image.value?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = SimpleDateFormat("dd.MM.yyyy").format(data.publicationDate),
                        style = TextStyle(color = Color.Gray, fontSize = 10.sp),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                    Text(
                        text = data.title,
                        style = TextStyle(color = Color.Black, fontSize = 16.sp)
                    )
                }

                Text(
                    text = data.content,
                    style = TextStyle(color = Color.Black, fontSize = 12.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadImage()
    }
}