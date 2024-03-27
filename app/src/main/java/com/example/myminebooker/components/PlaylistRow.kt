package com.example.myminebooker.components

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyPlaylist
import com.example.myminebooker.R
import com.example.myminebooker.table.models.Playlist

class PlaylistRow (
    private val ctx: Context,
    private val view: View,

    private val elTextViewPlaylistEachName: TextView = view.findViewById(R.id.TextViewEachPlaylistName)
) : RecyclerView.ViewHolder(view) {

    fun setup( thePlaylist: Playlist) {
        elTextViewPlaylistEachName.text = thePlaylist.name

        itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyPlaylist::class.java )
            intent.putExtra("id", thePlaylist.id)
            ctx.startActivity(intent)
        }
    }
}