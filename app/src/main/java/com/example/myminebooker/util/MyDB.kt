package com.example.myminebooker.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.table.TableBook
import com.example.myminebooker.table.TableReadlist

class MyDB (
    ctx: Context,

    DB_NAME: String = "Books.db",
    DB_VERSION: Int = 1

) : SQLiteOpenHelper (ctx, DB_NAME, null, DB_VERSION) {

    public val tableBook:TableBook = TableBook(this)
    public val tableReadlist:TableReadlist = TableReadlist(this)

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        var query = tableBook.initTable()
        db.execSQL(query)

        query = tableReadlist.initTable()
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var query = tableBook.upgradeTable()
        db.execSQL(query)

        query = tableReadlist.upgradeTable()
        db.execSQL(query)

        onCreate(db)
    }
}