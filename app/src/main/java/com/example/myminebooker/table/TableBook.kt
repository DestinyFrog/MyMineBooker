package com.example.myminebooker.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.models.Book
import com.example.myminebooker.models.BookRequest
import com.example.myminebooker.db.Crud

data class TableBook (
    // ARGUMENTS
    override val db: SQLiteOpenHelper,


    override val TABLE_NAME: String = "book",

    // TABLE COLUMNS
    override val COLUMN_ID: String = "_id",
    val COLUMN_TITLE:String = "title",
    val COLUMN_AUTHOR:String = "author",
) : Crud<Book, BookRequest>(db, TABLE_NAME, COLUMN_ID) {

    override fun initTable( db: SQLiteDatabase) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_TITLE VARCHAR(255)," +
                "$COLUMN_AUTHOR VARCHAR(255)" +
                ");"

        db.execSQL( query )
    }


    @SuppressLint("Range")
    override fun getAll(): List<Book> {
        val query = "SELECT "+COLUMN_ID+","+COLUMN_TITLE+","+COLUMN_AUTHOR+
                " FROM "+TABLE_NAME+ ";"

        val rdb = db.readableDatabase
        val cursor = rdb.rawQuery(query, null)

        val data:MutableList<Book> = mutableListOf()

        while( cursor.moveToNext() ) {
            val newBook = Book(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID) ),
                cursor.getString( cursor.getColumnIndex(COLUMN_TITLE) ),
                cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR) )
            )
            data.add(newBook)
        }

        cursor.close()
        rdb.close()
        return data.toList()
    }

    @SuppressLint("Range")
    override fun getOneById(id: Int): Book? {
        val query = "SELECT "+COLUMN_ID+","+COLUMN_TITLE+","+COLUMN_AUTHOR+
                " FROM "+TABLE_NAME+
                " WHERE "+COLUMN_ID+"=?"+
                ";"

        val rdb = db.readableDatabase
        val cursor = rdb.rawQuery(query, listOf( id.toString() ).toTypedArray())

        var data: Book? = null

        if ( cursor.count > 0 ) {
            if (cursor.moveToFirst()) {
                data = Book(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))
                )
            }
        }

        cursor.close()
        rdb.close()
        return data
    }

    override fun addOne(req: BookRequest): Long {
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, req.title)
        cv.put(COLUMN_AUTHOR, req.author)

        val wdb = db.writableDatabase
        val data = wdb.insert(TABLE_NAME, null, cv)
        wdb.close()

        return data
    }

    override fun updateOneByReq(id: Int, req: BookRequest): Int {
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, req.title)
        cv.put(COLUMN_AUTHOR, req.author)

        val wdb = db.writableDatabase
        val status = wdb.update(TABLE_NAME, cv, "$COLUMN_ID=?",
            listOf(id.toString()).toTypedArray() )
        wdb.close()

        return status
    }

    override fun deleteOneById(id: Int): Int {
        val wdb = db.writableDatabase
        val status = wdb.delete(TABLE_NAME, "$COLUMN_ID=?", listOf( id.toString() ).toTypedArray() )
        wdb.close()
        return status
    }
}