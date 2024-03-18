package com.example.myminebooker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myminebooker.models.Book
import com.example.myminebooker.util.MyDB

class CustomAdapterAllBooks (
    private val ctx:Context
) : RecyclerView.Adapter<CustomAdapterAllBooks.MyViewHolder>() {

    class MyViewHolder (
        view:View,

        val elTextViewBookEachId: TextView =  view.findViewById(R.id.textViewBookEachId),
        val elTextViewBookEachTitle: TextView = view.findViewById(R.id.textViewBookEachTitle),
        val elTextViewBookEachAuthor: TextView = view.findViewById(R.id.textViewBookEachAuthor),
        val elButtonUpdateBook: ImageButton = view.findViewById(R.id.buttonUpdateBook),
        val elButtonDeleteBook: ImageButton = view.findViewById(R.id.buttonDeleteBook)
    ) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.each_book_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        val data:List<Book> = MyDB(ctx).tableBook.getAll()
        return data.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data:List<Book> = MyDB(ctx).tableBook.getAll()
        val theBook = data[position]

        holder.elTextViewBookEachId.text = theBook.id.toString()
        holder.elTextViewBookEachTitle.text = theBook.title
        holder.elTextViewBookEachAuthor.text = theBook.author

        holder.elButtonUpdateBook.setOnClickListener {
            val intent = Intent( ctx, UpdateBook::class.java )
            intent.putExtra("id", theBook.id)
            ctx.startActivity(intent)
        }

        holder.elButtonDeleteBook.setOnClickListener {
            val status = MyDB(ctx).tableBook.deleteOneById( theBook.id )
            if ( status > 0 ) {
                Toast.makeText(ctx, "Deleted Successfully !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(ctx, "Error Deleting !!", Toast.LENGTH_SHORT).show()
            }

            reset()
        }
    }

    private fun reset() {
        notifyDataSetChanged()
    }
}