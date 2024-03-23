package com.example.myminebooker.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.myminebooker.table.models.Book
import com.example.myminebooker.table.models.BookRequest
import com.example.myminebooker.db.Crud
import com.example.myminebooker.util.MyDB

data class TableBook (
    // ARGUMENTS
    override val db: MyDB,

    override val tableName: String = "book",
    override val columnId: String = "_id",
    val columnTitle:String = "title",
    val columnAuthor:String = "author"
) : Crud<Book, BookRequest>(db, tableName, columnId) {
    override fun initTable( db: SQLiteDatabase) {
        val query = "CREATE TABLE IF NOT EXISTS $tableName (" +
                "$columnId INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$columnTitle VARCHAR(255)," +
                "$columnAuthor VARCHAR(255)" +
                ");"

        db.execSQL( query )
    }

    @SuppressLint("Range")
    override fun getAll(): List<Book> {
        val data:MutableList<Book> = mutableListOf()
        val query = "SELECT $columnId, $columnTitle,$columnAuthor" +
                " FROM $tableName;"

        val cursor = db.readableDatabase
            .rawQuery(query, null)

        while( cursor.moveToNext() ) {
            data.add(
                Book(
                    cursor.getInt(cursor.getColumnIndex(columnId) ),
                    cursor.getString( cursor.getColumnIndex(columnTitle) ),
                    cursor.getString(cursor.getColumnIndex(columnAuthor) )
                )
            )
        }

        cursor.close()
        return data.toList()
    }

    @SuppressLint("Range")
    override fun getOneById(id: Int): Book? {
        var data: Book? = null
        val query = "SELECT $columnId,$columnTitle,$columnAuthor" +
                " FROM $tableName" +
                " WHERE $columnId=?"+
                ";"

        val cursor = db.readableDatabase
            .rawQuery(
                query,
                arrayOf( id.toString() )
            )

        if ( cursor.count > 0 ) {
            if (cursor.moveToFirst()) {
                data = Book(
                    cursor.getInt(cursor.getColumnIndex(columnId)),
                    cursor.getString(cursor.getColumnIndex(columnTitle)),
                    cursor.getString(cursor.getColumnIndex(columnAuthor))
                )
            }
        }

        cursor.close()
        return data
    }

    override fun addOne(req: BookRequest): Long {
        val cv = ContentValues()
        cv.put(columnTitle, req.title)
        cv.put(columnAuthor, req.author)

        return db.writableDatabase
            .insert(tableName, null, cv)
    }

    override fun updateOneByReq(id: Int, req: BookRequest): Int {
        val cv = ContentValues()
        cv.put(columnTitle, req.title)
        cv.put(columnAuthor, req.author)

        return db.writableDatabase
            .update(
                tableName,
                cv,
                "$columnId=?",
                listOf(id.toString()).toTypedArray<String>()
            )
    }

    override fun deleteOneById(id: Int): Int {
        return db.writableDatabase
            .delete(
                tableName,
                "$columnId=?",
                listOf( id.toString() ).toTypedArray()
            )
    }
}