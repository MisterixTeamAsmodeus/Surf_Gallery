package com.misterixteam.asmodeus.surfgallery.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.ui.theme.SurfGalleryTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity(), SplashActivityContract.View {

    private val viewModel: SplashActivityContract.ViewModel = SplashViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        setContent {
            SurfGalleryTheme {
                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.ic_icon),
                contentDescription = "ic_splash"
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onActivityShow()
    }

    override fun showNewActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
        finish()
    }

    override fun getContext(): Context {
        return this
    }
}