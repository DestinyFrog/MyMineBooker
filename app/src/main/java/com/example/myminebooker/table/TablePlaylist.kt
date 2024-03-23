package com.example.myminebooker.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.myminebooker.table.models.Playlist
import com.example.myminebooker.table.models.PlaylistRequest
import com.example.myminebooker.db.Crud
import com.example.myminebooker.util.MyDB

data class TablePlaylist (
    override val db: MyDB,

    override val tableName: String = "playlist",
    override val columnId: String = "_id",
    private val columnName:String = "name"
) : Crud<Playlist, PlaylistRequest>(db, tableName, columnId) {
    override fun initTable( db: SQLiteDatabase) {
        val query = "CREATE TABLE IF NOT EXISTS $tableName (" +
                "$columnId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$columnName VARCHAR(255)" +
                ");"

        db.execSQL( query )
    }

    @SuppressLint("Range")
    override fun getAll(): List<Playlist> {
        val data:MutableList<Playlist> = mutableListOf()
        val query = "SELECT $columnId, $columnName" +
                " FROM $tableName" +
                ";"

        val cursor = db.readableDatabase
            .rawQuery(query, null)

        while( cursor.moveToNext() ) {
            data.add(
                Playlist(
                    cursor.getInt(cursor.getColumnIndex(columnId) ),
                    cursor.getString( cursor.getColumnIndex(columnName) )
                )
            )
        }

        cursor.close()
        return data.toList()
    }

    @SuppressLint("Range")
    override fun getOneById(id: Int): Playlist? {
        var data: Playlist? = null
        val query = "SELECT $columnId,$columnName" +
                " FROM $tableName" +
                " WHERE $columnId=?"+
                ";"

        val cursor = db.readableDatabase
            .rawQuery(
                query,
                listOf( id.toString() ).toTypedArray()
            )

        if ( cursor.count > 0 ) {
            if (cursor.moveToFirst()) {
                data = Playlist(
                    cursor.getInt(cursor.getColumnIndex(columnId) ),
                    cursor.getString( cursor.getColumnIndex(columnName) )
                )
            }
        }

        cursor.close()
        return data
    }

    override fun deleteOneById(id: Int): Int {
        return db.writableDatabase
            .delete(
                tableName,
                "$columnId=?",
                listOf( id.toString() ).toTypedArray()
            )
    }

    override fun updateOneByReq(id: Int, req: PlaylistRequest): Int {
        val cv = ContentValues()
        cv.put(columnName, req.name)

        return db.writableDatabase
            .update(
                tableName,
                cv,
                "$columnId=?",
                listOf(id.toString()).toTypedArray()
            )
    }

    override fun addOne(req: PlaylistRequest): Long {
        val cv = ContentValues()
        cv.put(columnName, req.name)

        return db.writableDatabase
            .insert(tableName, null, cv)
    }
}