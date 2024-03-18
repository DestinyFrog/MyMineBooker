package com.example.myminebooker.table

import android.database.sqlite.SQLiteOpenHelper
import com.example.myminebooker.models.Readlist
import com.example.myminebooker.models.ReadlistRequest
import com.example.myminebooker.util.Crud

data class TableReadlist (
    override val db: SQLiteOpenHelper,
    override val TABLE_NAME: String = "readlists",

    // TABLE COLUMNS
    override val COLUMN_ID: String = "_id",
    private val COLUMN_NAME:String = "name",

) : Crud<Readlist, ReadlistRequest >(db, TABLE_NAME, COLUMN_ID) {
    override fun getAll(): List<Readlist> {
        TODO("Not yet implemented")
    }

    override fun getOneById(id: Int): Readlist? {
        TODO("Not yet implemented")
    }

    override fun deleteOneById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun updateOneByReq(id: Int, req: ReadlistRequest): Int {
        TODO("Not yet implemented")
    }

    override fun addOne(req: ReadlistRequest): Long {
        TODO("Not yet implemented")
    }

}