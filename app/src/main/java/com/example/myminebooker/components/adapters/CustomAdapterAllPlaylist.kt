package com.example.myminebooker.components.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.R
import com.example.myminebooker.components.Playlist.PlaylistRow
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.util.MyDB

class CustomAdapterAllPlaylist (
    private val ctx: Context,
    private val db: MyDB
) : RecyclerView.Adapter<PlaylistRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistRow {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.each_playlist_row, parent, false)
        return PlaylistRow(ctx, view)
    }

    override fun getItemCount(): Int {
        val data:List<Playlist> = db.tablePlaylist.getAll()
        return data.count()
    }

    override fun onBindViewHolder(holder: PlaylistRow, position: Int) {
        val data:List<Playlist> = db.tablePlaylist.getAll()
        val thePlaylist = data[position]
        holder.setup( thePlaylist )
    }
}