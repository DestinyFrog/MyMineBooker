package com.example.myminebooker.components.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.OnlyBook
import com.example.myminebooker.R
import com.example.myminebooker.models.Book
import com.example.myminebooker.util.MyDB

class CustomAdapterAllBooks (
    private val ctx:Context,
    private val db:MyDB

) : RecyclerView.Adapter<CustomAdapterAllBooks.MyViewHolder>() {

    class MyViewHolder (
        view:View,

        val elTextViewBookEachId: TextView =  view.findViewById(R.id.textViewBookEachId),
        val elTextViewBookEachTitle: TextView = view.findViewById(R.id.textViewBookEachTitle),
        val elTextViewBookEachAuthor: TextView = view.findViewById(R.id.textViewBookEachAuthor)
    ) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.each_book_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        val data:List<Book> = db.tableBook.getAll()
        return data.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data:List<Book> = db.tableBook.getAll()
        val theBook = data[position]

        holder.elTextViewBookEachId.text = theBook.id.toString()
        holder.elTextViewBookEachTitle.text = theBook.title
        holder.elTextViewBookEachAuthor.text = theBook.author

        holder.itemView.setOnClickListener {
            val intent = Intent( ctx, OnlyBook::class.java )
            intent.putExtra("id", theBook.id)
            ctx.startActivity(intent)
        }
    }
}