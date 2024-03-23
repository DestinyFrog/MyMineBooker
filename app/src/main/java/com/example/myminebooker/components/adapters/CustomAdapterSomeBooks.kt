package com.example.myminebooker.components.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.R
import com.example.myminebooker.components.BookRow
import com.example.myminebooker.table.models.Book
import com.example.myminebooker.table.models.Playlist
import com.example.myminebooker.util.MyDB

class CustomAdapterSomeBooks (
    private val ctx:Context,
    private val db: MyDB,
    private val playlist: Playlist

) : RecyclerView.Adapter<BookRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRow {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.each_book_row, parent, false)
        return BookRow(ctx, view)
    }

    override fun getItemCount(): Int {
        val data:List<Book> = db.tablePlaylistAndBook.getBooksByPlaylist(playlist)
        return data.count()
    }

    override fun onBindViewHolder(holder: BookRow, position: Int) {
        val data:List<Book> = db.tablePlaylistAndBook.getBooksByPlaylist(playlist)
        val theBook = data[position]
        holder.setup(theBook)
    }
}