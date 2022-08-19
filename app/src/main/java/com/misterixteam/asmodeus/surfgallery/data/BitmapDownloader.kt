package com.misterixteam.asmodeus.surfgallery.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class BitmapDownloader(context: Context) {
    private val cashDirectory = context.cacheDir

    fun getBitmap(url: String, onEnd: (Bitmap) -> Unit) {
        val image = url.substring(url.lastIndexOf("/") + 1)
        val directory = File(cashDirectory, image)
        if (directory.exists()) {
            readBitmap(directory)?.let { onEnd(it) }
        } else {
            downloadBitmap(url) { bitmap ->
                bitmap?.let {
                    onEnd(it)
                    writeBitmap(it, directory)
                }
            }
        }
    }

    private fun downloadBitmap(url: String, onEnd: (Bitmap?) -> Unit) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            onEnd(null)
        }).launch {
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            onEnd(BitmapFactory.decodeStream(connection.inputStream))
            connection.disconnect()
        }
    }

    private fun readBitmap(directory: File): Bitmap? {
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(directory)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun writeBitmap(bitmap: Bitmap, directory: File) {
        if (!directory.exists())
            directory.createNewFile()
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(directory)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}