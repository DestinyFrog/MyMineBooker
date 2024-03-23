package com.example.myminebooker.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class Table (
    open val db: SQLiteOpenHelper,

    open val tableName: String = "",
    open val columnId:String = "_id",
) {
    open fun initTable( db:SQLiteDatabase ) {
        val query = "CREATE TABLE $tableName (" +
                "$columnId INTEGER PRIMARY KEY AUTO_INCREMENT" +
                ");"

        db.execSQL( query )
    }

    open fun upgradeTable( db:SQLiteDatabase ) {
        val query = "DROP TABLE IF EXISTS $tableName;"
        db.execSQL( query )
    }
}