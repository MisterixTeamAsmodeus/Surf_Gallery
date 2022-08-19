package com.misterixteam.asmodeus.surfgallery.ui.activity.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.model.TrailingIconState
import com.misterixteam.asmodeus.surfgallery.model.login.LoginData
import com.misterixteam.asmodeus.surfgallery.model.login.LoginErrors
import com.misterixteam.asmodeus.surfgallery.server.Server
import com.misterixteam.asmodeus.surfgallery.ui.activity.main.MainActivity

class LoginViewModel(view: LoginActivityContract.View) :
    LoginActivityContract.ViewModel {

    private val view: LoginActivityContract.View = view
    private val preference: LoginActivityContract.Data = AppPreference()
    private val server: LoginActivityContract.Server = Server()
    private var login = mutableStateOf(TextFieldValue(""))
    private var password = mutableStateOf(TextFieldValue(""))
    private var errors = LoginErrors()
    private var passwordState = mutableStateOf(TrailingIconState.HIDDEN)
    private var lastPasswordState = TrailingIconState.SHOWED_ACTIVE

    override fun getLogin(): MutableState<TextFieldValue> {
        return login
    }

    override fun getPassword(): MutableState<TextFieldValue> {
        return password
    }

    override fun getAuthError(): MutableState<Boolean> {
        return errors.auth
    }

    override fun getLoginError(): MutableState<Boolean> {
        return errors.login
    }

    override fun getPasswordError(): MutableState<Boolean> {
        return errors.password
    }

    override fun setLogin(value: TextFieldValue, maxCount: Int) {
        if (value.text.length <= maxCount) {
            login.value = TextFieldValue(
                text = value.text.filter { symbol -> symbol.isDigit() },
                selection = value.selection
            )
        }
    }

    override fun getPasswordMask(): MutableState<TrailingIconState> {
        return passwordState
    }

    override fun setPassword(value: TextFieldValue) {
        password.value = value
        passwordState.value = if (password.value.text.isEmpty())
            TrailingIconState.HIDDEN
        else
            lastPasswordState
    }

    override fun changeMask() {
        passwordState.value = if (passwordState.value == TrailingIconState.SHOWED_ACTIVE)
            TrailingIconState.SHOWED_DISABLE
        else
            TrailingIconState.SHOWED_ACTIVE
        lastPasswordState = passwordState.value
    }

    override fun onLoginClick() {
        errors.login.value = login.value.text.isEmpty()
        errors.password.value = password.value.text.isEmpty()
        if (!(errors.login.value && errors.password.value)) {
            view.blockUi()
            server.login(
                LoginData(
                    "+7${login.value.text}",
                    password.value.text.trim()
                ),
                {
                    preference.saveUser(view.getContext(), it)
                    view.openNewActivity(MainActivity::class.java)
                },
                {
                    view.openUi()
                    errors.auth.value = true
                }
            )
        }
    }

}