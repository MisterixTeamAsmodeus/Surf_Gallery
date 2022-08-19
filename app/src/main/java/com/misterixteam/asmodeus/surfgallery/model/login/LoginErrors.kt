package com.misterixteam.asmodeus.surfgallery.model.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LoginErrors(
    var auth: MutableState<Boolean> = mutableStateOf(false),
    var login: MutableState<Boolean> = mutableStateOf(false),
    var password: MutableState<Boolean> = mutableStateOf(false)
)
