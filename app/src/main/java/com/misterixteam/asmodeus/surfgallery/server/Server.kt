package com.misterixteam.asmodeus.surfgallery.server

import com.google.gson.GsonBuilder
import com.misterixteam.asmodeus.surfgallery.model.login.LoginData
import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.model.user.UserData
import com.misterixteam.asmodeus.surfgallery.ui.activity.login.LoginActivityContract
import com.misterixteam.asmodeus.surfgallery.ui.fragment.profile.ProfileFragmentContract
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class Server :
    LoginActivityContract.Server,
    ProfileFragmentContract.Server,
    PictureItemContract.Server {
    private val baseUrl = "https://pictures.chronicker.fun/api/"
    private val api = Retrofit.Builder().baseUrl(baseUrl).build().create(ServerApi::class.java)

    override fun login(
        body: LoginData,
        onSuccessful: (UserData) -> Unit,
        onError: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            onError()
        }).launch {
            val response = api.login(
                body
                    .toJson()
                    .toRequestBody(
                        "application/json".toMediaTypeOrNull()
                    )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onSuccessful(
                        GsonBuilder().create().fromJson(
                            response.body()!!.string(),
                            UserData::class.java
                        )
                    )
                } else {
                    onError()
                }
            }
        }
    }

    override fun logout(token: String, onSuccessful: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            onError()
        }).launch {
            val response = api.logout("Token $token")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onSuccessful()
                } else {
                    onError()
                }
            }
        }
    }

    override fun getPicture(
        token: String,
        onSuccessful: (Array<Picture>) -> Unit,
        onError: (Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            onError(1)
        }).launch {
            val response =
                api.getPicture("Token $token")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onSuccessful(
                        GsonBuilder().create().fromJson(
                            response.body()?.string(),
                            Array<Picture>::class.java
                        )
                    )
                } else {
                    if (response.code() == 401)
                        onError(404)
                    else
                        onError(1)
                }
            }
        }
    }
}