package com.misterixteam.asmodeus.surfgallery.model.user

import com.google.gson.GsonBuilder

data class User(
    val id: String,
    val phone: String,
    val email: String,
    val city: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val about: String
) {
    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}
