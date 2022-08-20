package com.misterixteam.asmodeus.surfgallery.ui.activity.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.model.NavigationItem
import com.misterixteam.asmodeus.surfgallery.ui.theme.SurfGalleryTheme

class MainActivity : ComponentActivity(), MainActivityContract.View {

    private lateinit var viewModel: MainActivityContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel(this)
        setContent {
            SurfGalleryTheme {
                HomeScreen()
            }
        }
    }

    override fun getContext(): Context {
        return this
    }

    @Composable
    private fun HomeScreen() {
        val fragmentLayoutId = "fragment"
        val navigationLayoutId = "navigation"
        val fragment = remember { viewModel.getFragment() }
        ConstraintLayout(
            constraintSet = getConstraintSet(fragmentLayoutId, navigationLayoutId),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White
                )
        ) {
            Box(modifier = Modifier.layoutId(fragmentLayoutId)) {
                fragment.value.DrawFragment()
            }

            DrawNavigationBar(
                id = navigationLayoutId,
                navigationItems = listOf(
                    NavigationItem("Главная", R.drawable.ic_home),
                    NavigationItem("Избранное", R.drawable.ic_favorite_diseble),
                    NavigationItem("Профиль", R.drawable.ic_profile)
                )
            )
        }
    }

    @Composable
    private fun getConstraintSet(
        fragmentLayoutId: String,
        navigationLayoutId: String
    ): ConstraintSet {
        return ConstraintSet {
            val fragment = createRefFor(fragmentLayoutId)
            val navigation = createRefFor(navigationLayoutId)

            constrain(fragment) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(navigation.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

            constrain(navigation) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        }
    }

    @Composable
    private fun DrawNavigationBar(id: String, navigationItems: List<NavigationItem>) {
        val selectedItem = remember { viewModel.getNavigationBarIndex() }
        NavigationBar(modifier = Modifier.layoutId(id)) {
            navigationItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                    },
                    label = { Text(item.title) },
                    selected = selectedItem.value == index,
                    onClick = { viewModel.changeFragment(index) }
                )
            }
        }
    }
}