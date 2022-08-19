package com.misterixteam.asmodeus.surfgallery.ui.activity.search

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.ui.theme.SurfGalleryTheme
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.search.SearchPictureItemView

class SearchActivity : ComponentActivity(), SearchActivityContract.View {

    private lateinit var pictureItemView: SearchActivityContract.Picture.View
    private lateinit var viewModel: SearchActivityContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pictureItemView = SearchPictureItemView(this)
        viewModel = SearchViewModel(
            this,
            pictureItemView.getViewModel()
        )
        setContent {
            SurfGalleryTheme {
                SearchScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    private fun SearchScreen() {
        val input = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val isEmptyResult = remember {
            viewModel.isPictureEmptyState()
        }

        Column(modifier = Modifier.padding(top = 8.dp)) {
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                IconButton(
                    onClick = { finish() },
                    modifier = Modifier.align(alignment = CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back"
                    )
                }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = input.value,
                    onValueChange = {
                        input.value = it
                        viewModel.textChange(it.text)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                            colorFilter = ColorFilter.tint(Color.Gray)
                        )
                    },
                    trailingIcon = {
                        if (input.value.text.isNotEmpty()) {
                            IconButton(onClick = { input.value = TextFieldValue("") }) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_clear),
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color(0xFFFBFBFB),
                        unfocusedLabelColor = Color(0x4D000000),
                        focusedLabelColor = Color(0x4D000000),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    placeholder = {
                        if (input.value.text.isEmpty())
                            Text(text = "Поиск")
                    }
                )
            }

            if (input.value.text.isNotEmpty()) {
                pictureItemView.DrawGrid()
            }
        }

        if (isEmptyResult.value) {
            DrawInfoElement(
                image = R.drawable.ic_bad,
                text = "По этому запросу нет результатов,\n" +
                        "попробуйте другой запрос"
            )
        }

        if (input.value.text.isEmpty()) {
            DrawInfoElement(
                image = R.drawable.ic_empty_search,
                text = "Введите ваш запрос"
            )
        }
    }

    @Composable
    fun DrawInfoElement(image: Int, text: String) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Center)) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "empty",
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .align(CenterHorizontally)
                )
                Text(
                    text = text,
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

    }

    override fun getContext(): Context {
        return this
    }
}