package com.example.myminebooker.util

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class Table (
    open val db: SQLiteOpenHelper,
    open val TABLE_NAME: String = "books",
    open val COLUMN_ID:String = "_id",
) {
    open fun initTable(): String {
        return "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                ");"
    }

    open fun upgradeTable(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME;"
    }
}