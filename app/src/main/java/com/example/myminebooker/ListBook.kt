package com.example.myminebooker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.adapters.CustomAdapterAllBooks
import com.example.myminebooker.util.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListBook : AppCompatActivity() {
    private lateinit var elRecyclerViewBookList: RecyclerView
    private lateinit var elFloatingActionButtonAdd: FloatingActionButton

    private val db = MyDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elRecyclerViewBookList = findViewById(R.id.recyclerViewBookList)
        elFloatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd)

        val adapter = CustomAdapterAllBooks(this, db)
        elRecyclerViewBookList.adapter = adapter
        elRecyclerViewBookList.layoutManager = LinearLayoutManager(this)

        elFloatingActionButtonAdd.setOnClickListener {
            val intent = Intent( this, AddBook::class.java )
            startActivity(intent)
        }
    }
}