package com.example.myminebooker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myminebooker.table.models.Playlist
import com.example.myminebooker.table.models.PlaylistRequest
import com.example.myminebooker.util.MyDB

class UpdatePlaylist : AppCompatActivity() {
    private lateinit var elEditTextPlaylistName: EditText
    private lateinit var elButtonAddPlaylist: Button

    private val db: MyDB = MyDB(this)
    private lateinit var data: Playlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_playlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elEditTextPlaylistName = findViewById(R.id.editTextPlaylistName)

        if ( !intent.hasExtra("id") ) {
            Toast.makeText(this, "No Data Found !!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val id = intent.getIntExtra("id", 0)
        val res = db.tablePlaylist.getOneById(id)

        if ( res == null ) {
            Toast.makeText(this, "Data Not Found in DB !!", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            data = res
        }

        elEditTextPlaylistName.setText( data.name )

        elButtonAddPlaylist.setOnClickListener {
            val inputReq = getInputs()
            val status = db.tablePlaylist.updateOneByReq(data.id, inputReq)

            if (status == -1) {
                Toast.makeText(this, "Failed Updating Data!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Updated Playlist Successfully !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getInputs(): PlaylistRequest {
        return PlaylistRequest (
            elEditTextPlaylistName.text.toString()
        )
    }
}