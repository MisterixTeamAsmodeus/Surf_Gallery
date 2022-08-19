package com.misterixteam.asmodeus.surfgallery.ui.activity.splash

import android.content.Context

interface SplashActivityContract {
    interface View {
        fun showNewActivity(clazz: Class<*>)
        fun getContext(): Context
    }

    interface ViewModel {
        fun onActivityShow()
    }

    interface Data {
        fun getUserToken(context: Context): String?
    }
}