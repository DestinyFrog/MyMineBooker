package com.example.myminebooker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.components.Book.ListBook
import com.example.myminebooker.components.Playlist.AddPlaylist
import com.example.myminebooker.components.adapters.CustomAdapterAllPlaylist
import com.example.myminebooker.util.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var elRecyclerViewPlaylistList:RecyclerView
    private lateinit var elFloatingActionButtonAdd:FloatingActionButton
    private lateinit var elFloatingActionButtonBookList:FloatingActionButton

    private lateinit var db: MyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = MyDB(this)
        db.onCreate(db.writableDatabase)

        elRecyclerViewPlaylistList = findViewById(R.id.recyclerViewPlaylistList)
        elFloatingActionButtonBookList = findViewById(R.id.floatingActionButtonBookList)
        elFloatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd)

        val adapter = CustomAdapterAllPlaylist(this, db)
        elRecyclerViewPlaylistList.adapter = adapter
        elRecyclerViewPlaylistList.layoutManager = LinearLayoutManager(this)

        elFloatingActionButtonBookList.setOnClickListener {
            val intent = Intent( this, ListBook::class.java )
            startActivity(intent)
        }

        elFloatingActionButtonAdd.setOnClickListener {
            val intent = Intent( this, AddPlaylist::class.java )
            startActivity(intent)
        }
    }
}