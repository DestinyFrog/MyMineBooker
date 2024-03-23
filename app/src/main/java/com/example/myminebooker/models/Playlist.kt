package com.example.myminebooker.models

import android.annotation.SuppressLint
import com.example.myminebooker.util.MyDB

data class Playlist (
    val id: Int,
    val name: String
) {
    @SuppressLint("Range")
    fun getBooks(db: MyDB): List<Book> {
        val query = "SELECT ${db.tableBook.TABLE_NAME}.${db.tableBook.COLUMN_ID}, ${db.tableBook.TABLE_NAME}.${db.tableBook.COLUMN_TITLE}, ${db.tableBook.TABLE_NAME}.${db.tableBook.COLUMN_AUTHOR}" +
                " FROM ${db.tablePlaylistAndBook.TABLE_NAME}" +
                " LEFT JOIN ${db.tableBook.TABLE_NAME}" +
                " ON ${db.tablePlaylistAndBook.TABLE_NAME}.${db.tablePlaylistAndBook.COLUMN_ID_BOOK}" +
                "=${db.tableBook.TABLE_NAME}.${db.tableBook.COLUMN_ID}" +
                " WHERE ${db.tablePlaylistAndBook.TABLE_NAME}.${db.tablePlaylistAndBook.COLUMN_ID_PLAYLIST}=$id" +
                ";"

        val rdb = db.readableDatabase
        val cursor = rdb.rawQuery(query, null)

        val data:MutableList<Book> = mutableListOf()

        while( cursor.moveToNext() ) {
            val newBook = Book(
                cursor.getInt(cursor.getColumnIndex(db.tableBook.COLUMN_ID) ),
                cursor.getString( cursor.getColumnIndex(db.tableBook.COLUMN_TITLE) ),
                cursor.getString(cursor.getColumnIndex(db.tableBook.COLUMN_AUTHOR) )
            )
            data.add(newBook)
        }

        cursor.close()
        rdb.close()
        return data
    }
}