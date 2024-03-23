package com.example.myminebooker.components

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyBook
import com.example.myminebooker.R
import com.example.myminebooker.models.Book

class BookRow (
    private val ctx: Context,
    private val view: View,

    private val elTextViewBookEachId: TextView =  view.findViewById(R.id.textViewBookEachId),
    private val elTextViewBookEachTitle: TextView = view.findViewById(R.id.textViewBookEachTitle),
    private val elTextViewBookEachAuthor: TextView = view.findViewById(R.id.textViewBookEachAuthor)
) : RecyclerView.ViewHolder(view) {

    fun setup(theBook: Book) {
        elTextViewBookEachId.text = theBook.id.toString()
        elTextViewBookEachTitle.text = theBook.title
        elTextViewBookEachAuthor.text = theBook.author

        itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyBook::class.java )
            intent.putExtra("id", theBook.id)
            ctx.startActivity(intent)
        }
    }
}