package com.example.myminebooker.components

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyBook
import com.example.myminebooker.R
import com.example.myminebooker.table.models.Book
import com.example.myminebooker.table.models.Playlist
import com.example.myminebooker.util.MyDB

class BookRow (
    private val ctx: Context,
    private val view: View,
    private val db: MyDB,
    private var playlist: Playlist? = null,

    private val elTextViewBookEachTitle: TextView = view.findViewById(R.id.textViewBookEachTitle),
    private val elTextViewBookEachAuthor: TextView = view.findViewById(R.id.textViewBookEachAuthor),
    private val elButtonBookRemovePlaylist: Button = view.findViewById(R.id.buttonBookRemovePlaylist)
) : RecyclerView.ViewHolder(view) {

    init {
        if (playlist == null) {
            (elButtonBookRemovePlaylist.parent as? ConstraintLayout)
                ?.removeView(elButtonBookRemovePlaylist)
        }
    }

    fun setup(theBook: Book) {
        elTextViewBookEachTitle.text = theBook.title
        elTextViewBookEachAuthor.text = theBook.author

        itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyBook::class.java )
            intent.putExtra("id", theBook.id)
            ctx.startActivity(intent)
        }

        elButtonBookRemovePlaylist.setOnClickListener {
            if (playlist != null) {
                val res = db.tablePlaylistAndBook.deleteByPlaylistAndBook(playlist!!, theBook)
                if (res > 0)
                    Toast.makeText(ctx, "Connection Deleted Succesfully !!", Toast.LENGTH_SHORT)
                        .show()

                (view.parent as? RecyclerView)
                    ?.removeView(view)

            }
        }
    }
}