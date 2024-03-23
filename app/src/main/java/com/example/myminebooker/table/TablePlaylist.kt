package com.example.myminebooker.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.models.PlaylistRequest
import com.example.myminebooker.db.Crud
import com.example.myminebooker.util.MyDB

data class TablePlaylist (
    override val db: MyDB,
    override val TABLE_NAME: String = "playlist",

    // TABLE COLUMNS
    override val COLUMN_ID: String = "_id",
    private val columnName:String = "name"
) : Crud<Playlist, PlaylistRequest>(db, TABLE_NAME, COLUMN_ID) {

    @SuppressLint("Range")
    override fun getAll(): List<Playlist> {
        val query = "SELECT $COLUMN_ID, $columnName" +
                " FROM $TABLE_NAME" +
                ";"

        val rdb = db.readableDatabase
        val cursor = rdb.rawQuery(query, null)
        val data:MutableList<Playlist> = mutableListOf()

        while( cursor.moveToNext() ) {
            val newPlaylist = Playlist(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID) ),
                cursor.getString( cursor.getColumnIndex(columnName) )
            )
            data.add(newPlaylist)
        }

        cursor.close()
        rdb.close()
        return data.toList()
    }

    @SuppressLint("Range")
    override fun getOneById(id: Int): Playlist? {
        val query = "SELECT "+COLUMN_ID+","+columnName +
                " FROM "+TABLE_NAME +
                " WHERE "+COLUMN_ID+"=?"+
                ";"

        val rdb = db.readableDatabase
        val cursor = rdb.rawQuery(query, listOf( id.toString() ).toTypedArray())

        var data:Playlist? = null

        if ( cursor.count > 0 ) {
            if (cursor.moveToFirst()) {
                data = Playlist(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID) ),
                    cursor.getString( cursor.getColumnIndex(columnName) )
                )
            }
        }

        cursor.close()
        rdb.close()
        return data
    }

    override fun deleteOneById(id: Int): Int {
        val wdb = db.writableDatabase
        val status = wdb.delete(TABLE_NAME, "$COLUMN_ID=?", listOf( id.toString() ).toTypedArray() )
        wdb.close()
        return status
    }

    override fun updateOneByReq(id: Int, req: PlaylistRequest): Int {
        val cv = ContentValues()
        cv.put(columnName, req.name)

        val wdb = db.writableDatabase
        val status = wdb.update(TABLE_NAME, cv, "$COLUMN_ID=?",
            listOf(id.toString()).toTypedArray() )
        wdb.close()

        return status
    }

    override fun addOne(req: PlaylistRequest): Long {
        val cv = ContentValues()
        cv.put(columnName, req.name)

        val wdb = db.writableDatabase
        val data = wdb.insert(TABLE_NAME, null, cv)
        wdb.close()

        return data
    }

    override fun initTable( db: SQLiteDatabase) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$columnName VARCHAR(255)" +
                ");"

        db.execSQL( query )
    }
}