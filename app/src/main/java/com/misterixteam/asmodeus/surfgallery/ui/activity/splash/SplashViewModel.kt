package com.misterixteam.asmodeus.surfgallery.ui.activity.splash

import com.misterixteam.asmodeus.surfgallery.data.AppPreference
import com.misterixteam.asmodeus.surfgallery.ui.activity.login.LoginActivity
import com.misterixteam.asmodeus.surfgallery.ui.activity.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val view: SplashActivityContract.View
) :
    SplashActivityContract.ViewModel {

    override fun onActivityShow() {
        var clazz: Class<*> = LoginActivity::class.java
        CoroutineScope(Dispatchers.Default).launch {
            delay(800)
            view.showNewActivity(clazz)
        }
        val preference: SplashActivityContract.Data = AppPreference()
        if (!preference.getUserToken(view.getContext()).isNullOrEmpty())
            clazz = MainActivity::class.java
    }
}