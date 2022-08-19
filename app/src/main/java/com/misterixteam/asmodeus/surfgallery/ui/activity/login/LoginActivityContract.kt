package com.misterixteam.asmodeus.surfgallery.ui.activity.login

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.misterixteam.asmodeus.surfgallery.model.TrailingIconState
import com.misterixteam.asmodeus.surfgallery.model.login.LoginData
import com.misterixteam.asmodeus.surfgallery.model.user.UserData

interface LoginActivityContract {
    interface View {
        fun openNewActivity(clazz: Class<*>)
        fun blockUi()
        fun openUi()
        fun getContext(): Context
    }

    interface ViewModel {
        fun getLogin(): MutableState<TextFieldValue>
        fun getPassword(): MutableState<TextFieldValue>
        fun getAuthError(): MutableState<Boolean>
        fun getLoginError(): MutableState<Boolean>
        fun getPasswordError(): MutableState<Boolean>
        fun getPasswordMask(): MutableState<TrailingIconState>
        fun setLogin(value: TextFieldValue, maxCount: Int)
        fun setPassword(value: TextFieldValue)
        fun onLoginClick()
        fun changeMask()
    }

    interface Data {
        fun saveUser(context: Context, userData: UserData)
    }

    interface Server {
        fun login(body: LoginData, onSuccessful: (UserData) -> Unit, onError: () -> Unit)
    }
}