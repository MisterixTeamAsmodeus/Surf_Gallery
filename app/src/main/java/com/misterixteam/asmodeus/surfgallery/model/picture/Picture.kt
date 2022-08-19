package com.misterixteam.asmodeus.surfgallery.model.picture

import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class Picture(
    val id: Int,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long
) {
    companion object {
        fun fromJson(json: String): Picture {
            return Gson().fromJson(json, Picture::class.java)
        }
    }

    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}