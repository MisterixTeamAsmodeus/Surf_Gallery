package com.misterixteam.asmodeus.surfgallery.ui.fragment.profile

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawButton
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawDialog
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawErrorMassage
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawTitle

class ProfileFragment(private val context: Context, token: String?) : BaseFragment,
    ProfileFragmentContract.View {

    private val viewModel: ProfileFragmentContract.ViewModel = ProfileViewModel(this, token)
    private val isError = mutableStateOf(false)

    override fun getContext(): Context {
        return context
    }

    override fun openActivity(clazz: Class<*>) {
        context.startActivity(Intent(context, clazz))
        (context as ComponentActivity).finish()
    }

    override fun showError() {
        isError.value = true
    }

    @Composable
    override fun DrawFragment() {
        val user = viewModel.getUserInfo()
        val image = remember {
            viewModel.getUserBitmap()
        }
        val error = remember {
            isError
        }
        val openDialog = remember { mutableStateOf(false) }

        if (openDialog.value)
            DrawDialog(
                openDialog = openDialog,
                onSuccessful = { viewModel.onLogoutClick() },
                text = "Вы точно хотите выйти из приложения?"
            )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
            Column {
                DrawTitle(text = "Профиль")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        image.value?.let {
                            Image(
                                bitmap = it,
                                contentDescription = user.avatar,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(125.dp)
                                    .height(125.dp)
                                    .padding(end = 16.dp)
                            )
                        }
                        Column {
                            Text(
                                text = user.firstName + "\n" + user.lastName,
                                style = TextStyle(color = Color.Black, fontSize = 18.sp)
                            )
                            Text(
                                text = "\"" + user.about + "\"",
                                style = TextStyle(color = Color.Gray, fontSize = 12.sp),
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }

                    DrawInfoElement(title = "Город", info = user.city)
                    DrawInfoElement(title = "Телефон", info = viewModel.maskPhone(user.phone))
                    DrawInfoElement(title = "Почта", info = user.email)
                }
            }

            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                if (error.value) {
                    DrawErrorMassage(
                        text = "Не удалось выйти, попробуйте еще раз",
                        backgroundColor = MaterialTheme.colorScheme.error
                    )
                }
                DrawButton(onClick = { openDialog.value = true }, text = "Выйти")
            }
        }
    }

    @Composable
    private fun DrawInfoElement(title: String, info: String) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Text(text = title, style = TextStyle(color = Color.Gray, fontSize = 12.sp))
            Text(
                text = info,
                style = TextStyle(color = Color.Black, fontSize = 18.sp)
            )
        }
    }
}