package com.misterixteam.asmodeus.surfgallery.ui.fragment.profile

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import com.misterixteam.asmodeus.surfgallery.model.user.User

interface ProfileFragmentContract {
    interface View {
        fun getContext(): Context
        fun openActivity(clazz: Class<*>)
        fun showError()
    }

    interface ViewModel {
        fun onLogoutClick()
        fun getUserInfo(): User
        fun getUserBitmap(): MutableState<ImageBitmap?>
        fun maskPhone(phone: String): String
    }

    interface Data {
        fun getUserInfo(context: Context): User
        fun getUserToken(context: Context): String?
        fun clearUserToken(context: Context)
    }

    interface Server {
        fun logout(
            token: String,
            onSuccessful: () -> Unit,
            onError: () -> Unit
        )
    }
}