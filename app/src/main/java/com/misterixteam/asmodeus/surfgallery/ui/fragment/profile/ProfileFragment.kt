package com.misterixteam.asmodeus.surfgallery.ui.fragment.profile

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.ui.fragment.BaseFragment

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
            ShowDialog(openDialog) {
                viewModel.onLogoutClick()
            }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                if (error.value) {
                    Text(
                        text = "Не удалось выйти, попробуйте еще раз",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RectangleShape
                            )
                            .padding(16.dp)
                    )
                }
                Button(
                    onClick = {
                        openDialog.value = true
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Выйти",
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Профиль",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 12.dp)
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
    }

    @Composable
    private fun ShowDialog(openDialog: MutableState<Boolean>, onSuccessful: () -> Unit) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Вы точно хотите выйти из приложения?")
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