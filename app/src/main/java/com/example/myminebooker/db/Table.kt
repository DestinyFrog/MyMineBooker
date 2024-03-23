package com.example.myminebooker.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class Table (
    // Arguments
    open val db: SQLiteOpenHelper,

    open val TABLE_NAME: String = "",
    open val COLUMN_ID:String = "_id",
) {
    open fun initTable( db:SQLiteDatabase ) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTO_INCREMENT" +
                ");"

        db.execSQL( query )
    }

    open fun upgradeTable( db:SQLiteDatabase ) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME;"
        db.execSQL( query )
    }
}