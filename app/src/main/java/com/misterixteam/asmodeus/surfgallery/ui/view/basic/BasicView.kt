package com.misterixteam.asmodeus.surfgallery.ui.view.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misterixteam.asmodeus.surfgallery.R

@Composable
fun DrawErrorMassage(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Red
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            style = TextStyle(color = textColor, fontSize = 14.sp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun DrawHintMessage(
    text: String,
    textColor: Color = Color.Gray,
    icon: Int = -1,
    iconSize: Dp = 32.dp,
    iconTint: Color = Color.Gray
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        ) {
            if (icon != -1) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = icon.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(iconSize)
                        .height(iconSize)
                        .padding(bottom = 8.dp),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }
            Text(
                text = text,
                style = TextStyle(color = textColor, fontSize = 12.sp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun DrawTitle(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    helperIconResource: Int = -1,
    onIconClick: () -> Unit = {},
    helperIconTint: Color = Color.Black,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp),
            style = TextStyle(color = textColor, fontSize = 24.sp),
            fontWeight = FontWeight.SemiBold
        )
        if (helperIconResource != -1) {
            IconButton(
                onClick = onIconClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(id = helperIconResource),
                    contentDescription = helperIconResource.toString(),
                    colorFilter = ColorFilter.tint(helperIconTint)
                )
            }
        }
    }
}

@Composable
fun DrawExitButton(
    onClick: () -> Unit,
    icon: Int = R.drawable.ic_back
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = icon.toString()
        )
    }
}

@Composable
fun DrawButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    shape: Shape = RectangleShape,
    isEnable: Boolean = true,
    buttonColor: Color = Color.Black,
    textColor: Color = Color.White
) {
    Button(
        enabled = isEnable,
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(buttonColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, end = 8.dp, start = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(color = textColor, fontSize = 16.sp),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun DrawDialog(
    openDialog: MutableState<Boolean>,
    onSuccessful: () -> Unit,
    text: String,
    confirmText: String = "да, точно",
    dismissText: String = "нет"
) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    onSuccessful()
                }
            ) {
                Text(confirmText.uppercase(), style = TextStyle(Color.Black))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(dismissText.uppercase(), style = TextStyle(Color.Black))
            }
        }
    )
}