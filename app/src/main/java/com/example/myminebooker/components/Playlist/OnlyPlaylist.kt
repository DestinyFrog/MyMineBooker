package com.example.myminebooker.components.Playlist

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.R
import com.example.myminebooker.components.adapters.CustomAdapterSomeBooks
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.util.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OnlyPlaylist : AppCompatActivity() {
    private lateinit var elTextViewPlaylistName: TextView
    private lateinit var elRecyclerViewBookList : RecyclerView
    private lateinit var elFloatingActionButtonDeletePlaylist:  FloatingActionButton
    private lateinit var elFloatingActionButtonUpdatePlaylist:  FloatingActionButton

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

        elTextViewPlaylistName = findViewById(R.id.textViewPlaylistName)
        elRecyclerViewBookList = findViewById(R.id.recyclerViewBookList)
        elFloatingActionButtonUpdatePlaylist = findViewById(R.id.floatingActionButtonUpdatePlaylist)
        elFloatingActionButtonDeletePlaylist = findViewById(R.id.floatingActionButtonDeletePlaylist)

        val id = intent.getIntExtra("id", 0)
        val data = db.tablePlaylist.getOneById(id)

        if ( data == null ) {
            Toast.makeText(this, "Playlist Not Found !!", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            playlistData = data
        }

        elTextViewPlaylistName.text = playlistData.name

        val adapter = CustomAdapterSomeBooks(this, db, playlistData)
        elRecyclerViewBookList.adapter = adapter
        elRecyclerViewBookList.layoutManager = LinearLayoutManager(this)

        elFloatingActionButtonUpdatePlaylist.setOnClickListener {
            val intent = Intent( this, UpdatePlaylist::class.java )
            intent.putExtra("id", playlistData.id)
            startActivity(intent)
        }

        elFloatingActionButtonDeletePlaylist.setOnClickListener {
            if ( db.tablePlaylist.deleteOneById( playlistData.id ) > 0 )
                Toast.makeText(this, "Deleted Successfully !!", Toast.LENGTH_SHORT)
                    .show()
            else
                Toast.makeText(this, "Error Deleting !!", Toast.LENGTH_SHORT)
                    .show()

            if ( db.tablePlaylistAndBook.deleteByPlaylist( playlistData ) > 0 )
                Toast.makeText(this, "Deleted Connections Successfully !!", Toast.LENGTH_SHORT)
                    .show()
            else
                Toast.makeText(this, "Error Deleting Connections !!", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}