package com.misterixteam.asmodeus.surfgallery.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.misterixteam.asmodeus.surfgallery.R
import com.misterixteam.asmodeus.surfgallery.model.TrailingIconState
import com.misterixteam.asmodeus.surfgallery.ui.MaskVisualTransformation
import com.misterixteam.asmodeus.surfgallery.ui.theme.SurfGalleryTheme
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawErrorMassage
import com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawTitle


class LoginActivity : ComponentActivity(), LoginActivityContract.View {

    private val viewModel: LoginActivityContract.ViewModel = LoginViewModel(this)
    private val isUiActive = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurfGalleryTheme {
                LoginScreen()
            }
        }
    }

    @Composable
    private fun LoginScreen() {
        val buttonId = "login_button"
        val loginViewId = "login_view"
        val errorId = "login_error"

        val isAuthError = remember { viewModel.getAuthError() }
        ConstraintLayout(
            constraintSet = getConstraintSet(buttonId, loginViewId, errorId),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White
                )
        ) {
            DrawInput(loginViewId)
            if (isAuthError.value) {
                DrawErrorMassage(
                    text = "Логин или пароль введен неправильно",
                    modifier = Modifier.layoutId(errorId),
                    backgroundColor = MaterialTheme.colorScheme.error
                )
            }
            val isEnableState = remember {
                isUiActive
            }
            com.misterixteam.asmodeus.surfgallery.ui.view.basic.DrawButton(
                onClick = { viewModel.onLoginClick() },
                text = "Войти",
                isEnable = isEnableState.value,
                modifier = Modifier.layoutId(buttonId)
            )

        }
    }

    @Composable
    private fun getConstraintSet(
        buttonId: String,
        loginViewId: String,
        errorId: String
    ): ConstraintSet {
        return ConstraintSet {
            val button = createRefFor(buttonId)
            val loginView = createRefFor(loginViewId)
            val error = createRefFor(errorId)

            constrain(loginView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(button.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

            constrain(button) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
                height = Dimension.fillToConstraints
            }

            constrain(error) {
                bottom.linkTo(button.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        }
    }

    @Composable
    private fun DrawInput(id: String) {
        val login = remember { viewModel.getLogin() }
        val password = remember { viewModel.getPassword() }
        val isLoginError = remember { viewModel.getLoginError() }
        val isPasswordError = remember { viewModel.getPasswordError() }
        val trailingIconState = remember { viewModel.getPasswordMask() }
        val loginMask = MaskVisualTransformation()
        Column(
            modifier = Modifier
                .layoutId(id)
        ) {
            DrawTitle(text = "Вход")

            AddTopMargin(45.dp)

            DrawEditText(
                label = "Логин",
                value = login,
                isError = isLoginError.value,
                maskTransformation = loginMask,
                onValueChange = { viewModel.setLogin(it, loginMask.getMaxCount()) },
                trailingIcon = null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            AddTopMargin(16.dp)

            DrawEditText(
                label = "Пароль",
                value = password,
                isError = isPasswordError.value,
                maskTransformation = trailingIconState.value.mask,
                onValueChange = { viewModel.setPassword(it) },
                trailingIcon = {
                    if (trailingIconState.value != TrailingIconState.HIDDEN) {
                        IconButton(onClick = { viewModel.changeMask() }) {
                            Image(
                                painter = painterResource(
                                    if (trailingIconState.value.isMaskActive)
                                        R.drawable.ic_visibility_off
                                    else
                                        R.drawable.ic_visibility_on
                                ),
                                contentDescription = "ic_visibility",
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_login_icon),
                contentDescription = "ic_login_icon",
                modifier = Modifier.fillMaxHeight()
            )
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DrawEditText(
        value: MutableState<TextFieldValue>,
        isError: Boolean,
        maskTransformation: VisualTransformation,
        onValueChange: (TextFieldValue) -> Unit,
        trailingIcon: @Composable (() -> Unit)?,
        label: String,
        keyboardOptions: KeyboardOptions
    ) {
        val state = remember { isUiActive }
        Column(Modifier.padding(horizontal = 16.dp)) {
            TextField(
                value = value.value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                enabled = state.value,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                isError = isError,
                trailingIcon = trailingIcon,
                visualTransformation = maskTransformation,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFBFBFB),
                    unfocusedLabelColor = Color(0x4D000000),
                    focusedLabelColor = Color(0x4D000000),
                    unfocusedIndicatorColor = Color(0xFFDFDFDF),
                    focusedIndicatorColor = Color(0xFFDFDFDF)
                ),
                keyboardOptions = keyboardOptions,
                label = { Text(text = label) },
            )
            if (isError) {
                Text(
                    "Поле не может быть пустым",
                    modifier = Modifier.padding(start = 24.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
        }
    }

    @Composable
    private fun AddTopMargin(size: Dp) {
        Spacer(modifier = Modifier.height(size))
    }

    override fun openNewActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
        finish()
    }

    override fun blockUi() {
        isUiActive.value = false
    }

    override fun openUi() {
        isUiActive.value = true
    }

    override fun getContext(): Context {
        return this
    }
}

