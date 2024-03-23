package com.example.myminebooker.db

import android.database.sqlite.SQLiteOpenHelper

abstract class Crud<T, U> (
    override val db: SQLiteOpenHelper,
    override val TABLE_NAME: String,
    override val COLUMN_ID: String
) : Table(db, TABLE_NAME, COLUMN_ID) {
    abstract fun getAll(): List<T>
    abstract fun getOneById(id:Int): T?
    abstract fun addOne(req:U): Long
    abstract fun updateOneByReq(id:Int, req:U): Int
    abstract fun deleteOneById(id:Int): Int
}