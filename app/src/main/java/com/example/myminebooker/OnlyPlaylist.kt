package com.example.myminebooker

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.components.adapters.CustomAdapterSomeBooks
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.util.MyDB

class OnlyPlaylist : AppCompatActivity() {
    private lateinit var elTextViewPlaylistId: TextView
    private lateinit var elTextViewPlaylistName: TextView
    private lateinit var elRecyclerViewBookList : RecyclerView

    private val db = MyDB(this)
    private lateinit var playlistData: Playlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_only_playlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elTextViewPlaylistId = findViewById(R.id.textViewPlaylistId)
        elTextViewPlaylistName = findViewById(R.id.textViewPlaylistName)
        elRecyclerViewBookList = findViewById(R.id.recyclerViewBookList)

        val id = intent.getIntExtra("id", 0)
        val data = db.tablePlaylist.getOneById(id)

        if ( data == null ) {
            Toast.makeText(this, "Playlist Not Found !!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        playlistData = data
        setup()
    }

    private fun setup() {
        elTextViewPlaylistId.text = playlistData.id.toString()
        elTextViewPlaylistName.text = playlistData.name

        val adapter = CustomAdapterSomeBooks(this, db, playlistData)
        elRecyclerViewBookList.adapter = adapter
        elRecyclerViewBookList.layoutManager = LinearLayoutManager(this)
    }
}