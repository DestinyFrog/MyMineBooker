package com.example.myminebooker.components.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyPlaylist
import com.example.myminebooker.R
import com.example.myminebooker.UpdatePlaylist
import com.example.myminebooker.models.Playlist
import com.example.myminebooker.util.MyDB

class CustomAdapterAllPlaylist (
    private val ctx: Context,
    private val db: MyDB
) : RecyclerView.Adapter<CustomAdapterAllPlaylist.MyViewHolder>() {

    class MyViewHolder (
        view: View,

        val elTextViewPlaylistEachId: TextView =  view.findViewById(R.id.TextViewPlaylistEachId),
        val elTextViewPlaylistEachName: TextView = view.findViewById(R.id.TextViewEachPlaylistName),
        val elButtonUpdatePlaylist: ImageButton = view.findViewById(R.id.ButtonUpdatePlaylist),
        val elButtonDeletePlaylist: ImageButton = view.findViewById(R.id.ButtonDeletePlaylist)
    ) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.each_playlist_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        val data:List<Playlist> = db.tablePlaylist.getAll()
        return data.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data:List<Playlist> = db.tablePlaylist.getAll()
        val thePlaylist = data[position]

        holder.itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyPlaylist::class.java )
            intent.putExtra("id", thePlaylist.id)
            ctx.startActivity(intent)
        }

        holder.elButtonDeletePlaylist.setOnClickListener {
            val status = db.tablePlaylist.deleteOneById( thePlaylist.id )

            if ( status > 0 ) {
                Toast.makeText(ctx, "Deleted Successfully !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(ctx, "Error Deleting !!", Toast.LENGTH_SHORT).show()
            }

            notifyDataSetChanged()
        }

        holder.elButtonUpdatePlaylist.setOnClickListener {
            val intent = Intent( ctx, UpdatePlaylist::class.java )
            intent.putExtra("id", thePlaylist.id)
            ctx.startActivity(intent)
        }

        holder.elTextViewPlaylistEachId.text = thePlaylist.id.toString()
        holder.elTextViewPlaylistEachName.text = thePlaylist.name
    }
}