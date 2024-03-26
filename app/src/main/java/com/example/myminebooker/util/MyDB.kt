package com.example.myminebooker.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.table.TableBook
import com.example.myminebooker.table.TablePlaylist
import com.example.myminebooker.table.TablePlaylistAndBook

class MyDB (
    ctx: Context,

    dbName: String = "Books.db",
    dbVersion: Int = 1
) : SQLiteOpenHelper (ctx, dbName, null, dbVersion) {

    val tableBook = TableBook(this)
    val tablePlaylist = TablePlaylist(this)
    val tablePlaylistAndBook = TablePlaylistAndBook(this, tableBook, tablePlaylist)

    override fun onCreate( db:SQLiteDatabase ) {
        tableBook.initTable(db)
        tablePlaylist.initTable(db)
        tablePlaylistAndBook.initTable(db)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        tableBook.upgradeTable(db)
        tablePlaylist.upgradeTable(db)
        tablePlaylistAndBook.upgradeTable(db)
    }
}