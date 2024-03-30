package com.example.myminebooker.components.Book

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
import com.example.myminebooker.R
import com.example.myminebooker.models.Book
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.models.PlaylistAndBookRequest
import com.example.myminebooker.util.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OnlyBook : AppCompatActivity() {
    private lateinit var elTextViewBookId: TextView
    private lateinit var elTextViewBookTitle: TextView
    private lateinit var elTextViewBookAuthor: TextView
    private lateinit var elTextViewBookNationality: TextView
    private lateinit var elTextViewBookDescription: TextView

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
        elTextViewBookNationality = findViewById(R.id.textViewBookNationality)
        elTextViewBookDescription = findViewById(R.id.textViewBookDescription)
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
        } else
            bookData = data

        elTextViewBookId.text = bookData.id.toString()
        elTextViewBookTitle.text = bookData.title
        elTextViewBookAuthor.text = bookData.author
        elTextViewBookNationality.text = bookData.nationality
        elTextViewBookDescription.text = bookData.description

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, playlistData )
        elSpinnerChoosePlaylist.adapter = adapter

        elFloatingActionButtonEditBook.setOnClickListener {
            val intent = Intent( this, UpdateBook::class.java )
            intent.putExtra("id", data.id)
            startActivity(intent)
        }

        elFloatingActionButtonDeleteBook.setOnClickListener {
            if ( db.tableBook.deleteOneById( data.id ) > 0 )
                Toast.makeText(this, "Deleted Successfully !!", Toast.LENGTH_SHORT)
                    .show()
            else
                Toast.makeText(this, "Error Deleting !!", Toast.LENGTH_SHORT)
                    .show()

            if ( db.tablePlaylistAndBook.deleteByBook( data ) > 0 )
                Toast.makeText(this, "Connections Deleted Succesfully !!", Toast.LENGTH_SHORT)
                    .show()
            else
                Toast.makeText(this, "Error Deleting Connections !!", Toast.LENGTH_SHORT)
                    .show()
        }

        elFloatingActionButtonSearchBook.setOnClickListener {
            val url = "https://openlibrary.org/search?q=${data.title}&mode=everything"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        elButtonAddToPlaylist.setOnClickListener {
            val req = getInputs()
            val res = db.tablePlaylistAndBook.addOne(req)

            if ( res.toInt() == -1 )
                Toast.makeText(this, "Book Not Added !!", Toast.LENGTH_SHORT)
                    .show()
            else
                Toast.makeText(this, "Book Added Successfully", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun getInputs(): PlaylistAndBookRequest {
        val item = elSpinnerChoosePlaylist.getSelectedItem().toString()
        val id = ( item.split(".")[0] ).toInt()

        return PlaylistAndBookRequest (
            bookData.id,
            id
        )
    }
}