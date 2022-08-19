package com.misterixteam.asmodeus.surfgallery.server

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ServerApi {
    @POST("auth/login")
    suspend fun login(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ResponseBody>

    @GET("picture")
    suspend fun getPicture(@Header("Authorization") token: String): Response<ResponseBody>
}