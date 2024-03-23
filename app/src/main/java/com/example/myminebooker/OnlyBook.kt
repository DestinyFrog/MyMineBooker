package com.example.myminebooker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myminebooker.table.models.Book
import com.example.myminebooker.table.models.Playlist
import com.example.myminebooker.table.models.PlaylistAndBookRequest
import com.example.myminebooker.util.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OnlyBook : AppCompatActivity() {
    private lateinit var elTextViewBookId: TextView
    private lateinit var elTextViewBookTitle: TextView
    private lateinit var elTextViewBookAuthor: TextView
    private lateinit var elSpinnerChoosePlaylist: Spinner
    private lateinit var elButtonAddToPlaylist: Button
    private lateinit var elFloatingActionButtonEditBook: FloatingActionButton
    private lateinit var elFloatingActionButtonDeleteBook: FloatingActionButton
    private lateinit var elFloatingActionButtonSearchBook: FloatingActionButton

    private val db = MyDB(this)
    private lateinit var bookData: Book

    private lateinit var playlistData: List<Playlist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_only_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playlistData = db.tablePlaylist.getAll()

        elTextViewBookId = findViewById(R.id.textViewBookId)
        elTextViewBookTitle = findViewById(R.id.textViewBookTitle)
        elTextViewBookAuthor = findViewById(R.id.textViewBookAuthor)
        elSpinnerChoosePlaylist = findViewById(R.id.spinnerChoosePlaylist)
        elButtonAddToPlaylist = findViewById(R.id.buttonAddToPlaylist)
        elFloatingActionButtonEditBook = findViewById(R.id.floatingActionButtonEditBook)
        elFloatingActionButtonDeleteBook = findViewById(R.id.floatingActionButtonDeleteBook)
        elFloatingActionButtonSearchBook = findViewById(R.id.floatingActionButtonSearchBook)

        val id = intent.getIntExtra("id", 0)
        val data = db.tableBook.getOneById(id)

        if ( data == null ) {
            Toast.makeText(this, "Book Not Found !!", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            bookData = data
            setup()
        }

        elFloatingActionButtonEditBook.setOnClickListener {
            val intent = Intent( this, UpdateBook::class.java )
            intent.putExtra("id", data.id)
            startActivity(intent)
        }

        elFloatingActionButtonDeleteBook.setOnClickListener {
            val status = db.tableBook.deleteOneById( data.id )
            if ( status > 0 ) {
                Toast.makeText(this, "Deleted Successfully !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error Deleting !!", Toast.LENGTH_SHORT).show()
            }
        }

        elFloatingActionButtonSearchBook.setOnClickListener {
            val url = "https://openlibrary.org/search?q=${data.title}&mode=everything"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        elButtonAddToPlaylist.setOnClickListener {
            val req = getInputs()
            if (req == null) {
                Toast.makeText(this, "Playlist Not Found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val res = db.tablePlaylistAndBook.addOne(req)
                if ( res.toInt() == -1 ) {
                    Toast.makeText(this, "Book Not Added !!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Book Added Succesfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setup() {
        elTextViewBookId.text = bookData.id.toString()
        elTextViewBookTitle.text = bookData.title
        elTextViewBookAuthor.text = bookData.author

        val namePlaylists: MutableList<String> = mutableListOf()

        for ( eachPlay in playlistData ) {
            namePlaylists.add( eachPlay.name )
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, namePlaylists )
        elSpinnerChoosePlaylist.adapter = adapter
    }

    private fun getInputs(): PlaylistAndBookRequest? {
        val item = elSpinnerChoosePlaylist.getSelectedItem().toString()
        var chosenPlaylist: Int? = null

        for ( eachPlaylist in playlistData ) {
            if ( eachPlaylist.name == item ) {
                chosenPlaylist = eachPlaylist.id
            }
        }

        if (chosenPlaylist == null)
            return null

        return PlaylistAndBookRequest (
            bookData.id,
            chosenPlaylist
        )
    }
}