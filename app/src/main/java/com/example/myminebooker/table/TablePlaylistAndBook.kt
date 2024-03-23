package com.example.myminebooker.table

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.models.PlaylistAndBookRequest
import com.example.myminebooker.db.Table

data class TablePlaylistAndBook (
    override val db: SQLiteOpenHelper,
    val tableBook:TableBook,
    val tablePlaylist:TablePlaylist,

    override val TABLE_NAME: String = "playlist_and_book",

    // TABLE COLUMNS
    // override val COLUMN_ID: String = "_id",
    val COLUMN_ID_BOOK: String = "_id_book",
    val COLUMN_ID_PLAYLIST: String = "_id_playlist"
) : Table(db) {
    override fun initTable( db:SQLiteDatabase ) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID_BOOK INTEGER," +
                "$COLUMN_ID_PLAYLIST INTEGER," +
                "FOREIGN KEY ($COLUMN_ID_BOOK) REFERENCES ${tableBook.TABLE_NAME}(${tableBook.COLUMN_ID})," +
                "FOREIGN KEY ($COLUMN_ID_PLAYLIST) REFERENCES ${tablePlaylist.TABLE_NAME}(${tablePlaylist.COLUMN_ID})" +
                ");"

        db.execSQL( query )
    }

    fun addOne( req: PlaylistAndBookRequest ): Long {
        val cv = ContentValues()
        cv.put(COLUMN_ID_BOOK, req.idBook)
        cv.put(COLUMN_ID_PLAYLIST, req.idPlaylist)

        val wdb = db.writableDatabase
        val data = wdb.insert(TABLE_NAME, null, cv)
        wdb.close()

        return data
    }
}