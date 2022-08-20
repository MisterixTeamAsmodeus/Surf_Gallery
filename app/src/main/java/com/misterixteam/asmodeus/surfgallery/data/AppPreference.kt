package com.misterixteam.asmodeus.surfgallery.data

import android.content.Context
import com.google.gson.Gson
import com.misterixteam.asmodeus.surfgallery.model.user.User
import com.misterixteam.asmodeus.surfgallery.model.user.UserData
import com.misterixteam.asmodeus.surfgallery.ui.activity.login.LoginActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.activity.main.MainActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.activity.splash.SplashActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.fragment.home.HomeFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.fragment.profile.ProfileFragmentContract

class AppPreference : SplashActivityContract.Data, LoginActivityContract.Data,
    MainActivityContract.Data, HomeFragmentContract.Data, ProfileFragmentContract.Data {

    companion object {
        private const val APP_PREFERENCE = "APP_PREFERENCE"
        private const val TOKEN = "TOKEN"
        private const val USER_INFO = "USER_INFO"
    }

    override fun getUserInfo(context: Context): User {
        return Gson().fromJson(
            context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
                .getString(USER_INFO, null), User::class.java
        )

    }

    override fun getUserToken(context: Context): String? {
        return context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            .getString(TOKEN, null)
    }

    override fun clearUserToken(context: Context) {
        saveUserToken(context, null)
    }

    override fun saveUser(context: Context, userData: UserData) {
        saveUserToken(context, userData.token)
        saveUserInfo(context, userData.user_info)
    }

    private fun saveUserToken(context: Context, token: String?) {
        val editor = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    private fun saveUserInfo(context: Context, userData: User) {
        val editor = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit()
        editor.putString(USER_INFO, userData.toJson())
        editor.apply()
    }
}