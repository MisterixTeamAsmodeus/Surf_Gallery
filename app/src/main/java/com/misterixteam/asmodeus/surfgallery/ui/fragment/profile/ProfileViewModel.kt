package com.misterixteam.asmodeus.surfgallery.ui.fragment.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.data.BitmapDownloader
import com.misterixteam.asmodeus.surfgallery.model.user.User
import com.misterixteam.asmodeus.surfgallery.server.Server
import com.misterixteam.asmodeus.surfgallery.ui.activity.login.LoginActivity

class ProfileViewModel(private val view: ProfileFragmentContract.View, private val token: String?) :
    ProfileFragmentContract.ViewModel {
    private val preference: ProfileFragmentContract.Data = AppPreference()
    private val server: ProfileFragmentContract.Server = Server()
    private val bitmapDownloader = BitmapDownloader(view.getContext())
    private val user = preference.getUserInfo(view.getContext())
    private val bitmap: MutableState<ImageBitmap?> = mutableStateOf(null)

    override fun onLogoutClick() {
        token?.let {
            server.logout(it, {
                preference.clearUserToken(view.getContext())
                view.openActivity(LoginActivity::class.java)
            }, {
                view.showError()
            })
        }
    }

    override fun getUserInfo(): User {
        loadImage()
        return user
    }

    override fun getUserBitmap(): MutableState<ImageBitmap?> {
        return bitmap
    }

    override fun maskPhone(phone: String): String {
        return phone[0] + " " + phone[1] + " (" + phone[2] + phone[3] + phone[4] + ") " + phone[5] + phone[6] + phone[7] + " " + phone[8] + phone[9] + " " + phone[10] + phone[11]
    }

    private fun loadImage() {
        bitmapDownloader.getBitmap(user.avatar) {
            bitmap.value = it.asImageBitmap()
        }
    }
}