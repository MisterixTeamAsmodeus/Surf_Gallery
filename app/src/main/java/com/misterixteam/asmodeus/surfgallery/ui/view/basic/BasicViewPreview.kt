package com.misterixteam.asmodeus.surfgallery.ui.view.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.misterixteam.asmodeus.surfgallery.R


@Preview
@Composable
private fun DrawDialogPreview() {
    val state = remember {
        mutableStateOf(true)
    }
    DrawDialog(
        openDialog = state,
        onSuccessful = { },
        text = "Вы точно хотите выйти из аккаунта?"
    )
}

@Preview
@Composable
private fun DrawErrorMassagePreview() {
    DrawErrorMassage(text = "Ошибка загрузки")
}

@Preview
@Composable
private fun DrawButtonPreview() {
    DrawButton(modifier = Modifier, onClick = {}, text = "Войти")
}

@Preview
@Composable
private fun DrawExitButtonPreview() {
    DrawExitButton(onClick = { })
}

@Preview
@Composable
private fun DrawTitlePreview() {
    DrawTitle(text = "Галлерея")
}

@Preview
@Composable
private fun DrawTitleIconPreview() {
    DrawTitle(text = "Галлерея", helperIconResource = R.drawable.ic_search)
}

@Preview
@Composable
private fun DrawHintMessagePreview() {
    DrawHintMessage("Ошибка загрузки")
}

@Preview
@Composable
private fun DrawHintMessageIconPreview() {
    DrawHintMessage("Ошибка загрузки", icon = R.drawable.ic_search)
}