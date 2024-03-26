package com.example.myminebooker.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class InterTable (
    open val db: SQLiteOpenHelper,
    open val table1: Table,
    open val table2: Table,

    open val tableName: String = "",
    open val columnId: String = "_id",
    open val columnIdTable1:String = "${table1.columnId}_table1",
    open val columnIdTable2:String = "${table2.columnId}_table2"
) {
    open fun initTable(db:SQLiteDatabase) {
        val query = "CREATE TABLE IF NOT EXISTS $tableName (" +
                "$columnId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$columnIdTable1 INTEGER," +
                "$columnIdTable2 INTEGER," +
                "FOREIGN KEY ($columnIdTable1) REFERENCES ${table1.tableName}(${table1.columnId})," +
                "FOREIGN KEY ($columnIdTable2) REFERENCES ${table2.tableName}(${table2.columnId})" +
                ");"

        db.execSQL( query )
    }

    open fun upgradeTable(db:SQLiteDatabase) {
        val query = "DROP TABLE IF EXISTS $tableName;"
        db.execSQL( query )
    }
}