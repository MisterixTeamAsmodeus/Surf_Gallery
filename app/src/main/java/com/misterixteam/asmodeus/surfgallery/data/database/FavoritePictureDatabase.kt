package com.misterixteam.asmodeus.surfgallery.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.DATABASE_NAME
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.DATABASE_VERSION
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_CONTENT
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_ID
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_IMAGE_ID
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_PUBLICATION_DATA
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_TITLE
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.KEY_URL
import com.misterixteam.asmodeus.surfgallery.data.database.FavoritePictureDatabaseConst.TABLE_PICTURE
import com.misterixteam.asmodeus.surfgallery.model.picture.Picture
import com.misterixteam.asmodeus.surfgallery.ui.view.picture.basic.PictureItemContract

class FavoritePictureDatabase(context: Context, userId: String) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME + userId,
        null,
        DATABASE_VERSION
    ),
    PictureItemContract.Data {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(
            "CREATE TABLE $TABLE_PICTURE (" +
                    "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$KEY_IMAGE_ID NUMERIC," +
                    "$KEY_TITLE TEXT," +
                    "$KEY_CONTENT TEXT," +
                    "$KEY_URL TEXT," +
                    "$KEY_PUBLICATION_DATA NUMERIC" +
                    ")"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase!!.execSQL("DROP TABLE IF EXISTS $TABLE_PICTURE")
        onCreate(sqLiteDatabase)
    }

    override fun savePicture(picture: Picture) {
        writableDatabase.insert(
            TABLE_PICTURE,
            null,
            getContentValues(picture)
        )
    }

    override fun deletePicture(picture: Picture) {
        writableDatabase.delete(
            TABLE_PICTURE,
            "$KEY_IMAGE_ID=${picture.id}",
            null
        )
    }

    override fun loadPictures(): Array<Picture> {
        val pictures = ArrayList<Picture>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_PICTURE",
            null
        )
        addPictureToArray(pictures, cursor)
        return pictures.toArray(arrayOf())
    }

    private fun getContentValues(picture: Picture): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(KEY_IMAGE_ID, picture.id)
        contentValues.put(KEY_TITLE, picture.title)
        contentValues.put(KEY_CONTENT, picture.content)
        contentValues.put(KEY_URL, picture.photoUrl)
        contentValues.put(KEY_PUBLICATION_DATA, picture.publicationDate)
        return contentValues
    }

    private fun addPictureToArray(pictures: ArrayList<Picture>, cursor: Cursor) {
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(KEY_IMAGE_ID)
            val titleIndex = cursor.getColumnIndex(KEY_TITLE)
            val contentIndex = cursor.getColumnIndex(KEY_CONTENT)
            val urlIndex = cursor.getColumnIndex(KEY_URL)
            val publicationDataIndex = cursor.getColumnIndex(KEY_PUBLICATION_DATA)
            do {
                pictures.add(
                    Picture(
                        cursor.getInt(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(contentIndex),
                        cursor.getString(urlIndex),
                        cursor.getLong(publicationDataIndex)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}