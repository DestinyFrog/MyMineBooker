package com.example.myminebooker.components.Book

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myminebooker.R
import com.example.myminebooker.models.Book
import com.example.myminebooker.models.BookRequest
import com.example.myminebooker.util.MyDB

class UpdateBook : AppCompatActivity() {
    private lateinit var elEditTextBookTitle:EditText
    private lateinit var elEditTextBookAuthor:EditText
    private lateinit var elEditTextBookNationality:EditText
    private lateinit var elEditTextBookDescription:EditText
    private lateinit var elButtonUpdateBook:Button

    private var db = MyDB(this)
    private lateinit var data: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elEditTextBookTitle = findViewById(R.id.EditTextBookTitle)
        elEditTextBookAuthor = findViewById(R.id.EditTextBookAuthor)
        elEditTextBookNationality = findViewById(R.id.EditTextBookNationality)
        elEditTextBookDescription = findViewById(R.id.EditTextBookDescription)
        elButtonUpdateBook = findViewById(R.id.ButtonUpdateBook)

        if ( !intent.hasExtra("id") ) {
            Toast.makeText(this, "No Data Found !!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val id = intent.getIntExtra("id", 0)
        val res =  db.tableBook.getOneById(id)

        if ( res == null ) {
            Toast.makeText(this, "Data Not Found in DB !!", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            data = res
        }

        elEditTextBookTitle.setText( data.title )
        elEditTextBookAuthor.setText( data.author )
        elEditTextBookNationality.setText( data.nationality )
        elEditTextBookDescription.setText( data.description )

        elButtonUpdateBook.setOnClickListener {
            val inputReq = getInputs()
            val status = db.tableBook.updateOneByReq(data.id, inputReq)

            if (status == -1) {
                Toast.makeText(this, "Failed Updating Data!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Updated Book Successfully !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getInputs(): BookRequest {
        return BookRequest(
            elEditTextBookTitle.text.toString(),
            elEditTextBookAuthor.text.toString(),
            elEditTextBookNationality.text.toString(),
            elEditTextBookDescription.text.toString()
        )
    }
}