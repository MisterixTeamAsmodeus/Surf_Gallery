package com.misterixteam.asmodeus.surfgallery.model.login

import com.google.gson.GsonBuilder

data class LoginData(
    private val phone: String,
    private val password: String
) {
    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}
