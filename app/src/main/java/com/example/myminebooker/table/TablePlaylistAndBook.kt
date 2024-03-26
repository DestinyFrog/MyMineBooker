package com.example.myminebooker.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.db.InterTable
import com.example.myminebooker.table.models.PlaylistAndBookRequest
import com.example.myminebooker.table.models.Book
import com.example.myminebooker.table.models.Playlist

data class TablePlaylistAndBook (
    override val db: SQLiteOpenHelper,
    val tableBook:TableBook,
    val tablePlaylist:TablePlaylist,

    override val tableName: String = "playlist_and_book",
    override val columnId: String = "_id",
    override val columnIdTable1: String = "_id_book",
    override val columnIdTable2: String = "_id_playlist",

    val columnTableBook: String = columnIdTable1,
    val columnTablePlaylist: String = columnIdTable2
) : InterTable(db, tableBook, tablePlaylist) {

    @SuppressLint("Range")
    fun getBooksByPlaylist(playlist: Playlist): List<Book> {
        val data:MutableList<Book> = mutableListOf()
        val query = "SELECT T.$columnId, T1.${tableBook.columnId}, T1.${tableBook.columnTitle}, T1.${tableBook.columnAuthor}" +
                " FROM $tableName T" +
                " INNER JOIN ${tableBook.tableName} T1" +
                " ON T1.${tableBook.columnId} = T.${tableBook.columnId}" +
                " WHERE T.$columnTablePlaylist = ${playlist.id}" +
                ";"

        val cursor = db.readableDatabase
            .rawQuery(query, null)

        while( cursor.moveToNext() ) {
            data.add(
                Book (
                    cursor.getInt(cursor.getColumnIndex(tableBook.columnId) ),
                    cursor.getString(cursor.getColumnIndex(tableBook.columnTitle) ),
                    cursor.getString(cursor.getColumnIndex(tableBook.columnAuthor) )
                )
            )
        }

        cursor.close()
        return data.toList()
    }

    fun addOne(req: PlaylistAndBookRequest): Long {
        val cv = ContentValues()
        cv.put(columnTableBook, req.idBook)
        cv.put(columnTablePlaylist, req.idPlaylist)

        val wdb = db.writableDatabase
        val data = wdb.insert(tableName, null, cv)
        wdb.close()

        return data
    }

    fun deleteByBook( bookData: Book ): Int {
        return db.writableDatabase
            .delete(
                tableName,
                "$columnTableBook=?",
                arrayOf( bookData.id.toString() )
            )
    }

    fun deleteByPlaylist( playlistData: Playlist ): Int {
        return db.writableDatabase
            .delete(
                tableName,
                "$columnTablePlaylist=?",
                arrayOf( playlistData.id.toString() )
            )
    }


}