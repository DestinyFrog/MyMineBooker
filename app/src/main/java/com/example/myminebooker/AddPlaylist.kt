package com.example.myminebooker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myminebooker.models.PlaylistRequest
import com.example.myminebooker.util.MyDB

class AddPlaylist : AppCompatActivity() {
    private lateinit var elEditTextPlaylistName: EditText
    private lateinit var elButtonAddPlaylist: Button

    private var db = MyDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_playlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elEditTextPlaylistName = findViewById(R.id.EditTextPlaylistName)
        elButtonAddPlaylist = findViewById(R.id.ButtonAddPlaylist)

        elButtonAddPlaylist.setOnClickListener {
            val inputReq = getInputs()
            val res = db.tablePlaylist.addOne(inputReq)

            if (res.toInt() == -1) {
                Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Added Playlist Successfully !", Toast.LENGTH_SHORT)
                    .show()
                resetInputs()
            }
        }
    }

    private fun getInputs(): PlaylistRequest {
        return PlaylistRequest(
            elEditTextPlaylistName.text.toString()
        )
    }

    private fun resetInputs() {
        elEditTextPlaylistName.setText("")
    }
}