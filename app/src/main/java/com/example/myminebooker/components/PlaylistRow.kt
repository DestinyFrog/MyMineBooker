package com.example.myminebooker.components

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyPlaylist
import com.example.myminebooker.R
import com.example.myminebooker.UpdatePlaylist
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.util.MyDB

class PlaylistRow (
    private val ctx: Context,
    private val view: View,
    private val db: MyDB,

    private val elTextViewPlaylistEachId: TextView =  view.findViewById(R.id.TextViewPlaylistEachId),
    private val elTextViewPlaylistEachName: TextView = view.findViewById(R.id.TextViewEachPlaylistName),
    private val elButtonUpdatePlaylist: ImageButton = view.findViewById(R.id.ButtonUpdatePlaylist),
    private val elButtonDeletePlaylist: ImageButton = view.findViewById(R.id.ButtonDeletePlaylist)
) : RecyclerView.ViewHolder(view) {

    fun setup( thePlaylist:Playlist ) {
        elTextViewPlaylistEachId.text = thePlaylist.id.toString()
        elTextViewPlaylistEachName.text = thePlaylist.name

        itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyPlaylist::class.java )
            intent.putExtra("id", thePlaylist.id)
            ctx.startActivity(intent)
        }

        elButtonDeletePlaylist.setOnClickListener {
            val status = db.tablePlaylist.deleteOneById( thePlaylist.id )

            if ( status > 0 ) {
                Toast.makeText(ctx, "Deleted Successfully !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(ctx, "Error Deleting !!", Toast.LENGTH_SHORT).show()
            }
        }

        elButtonUpdatePlaylist.setOnClickListener {
            val intent = Intent( ctx, UpdatePlaylist::class.java )
            intent.putExtra("id", thePlaylist.id)
            ctx.startActivity(intent)
        }
    }
}