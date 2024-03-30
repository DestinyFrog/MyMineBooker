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
import com.example.myminebooker.models.BookRequest
import com.example.myminebooker.util.MyDB

class AddBook : AppCompatActivity() {
    private lateinit var elEditTextBookTitle:EditText
    private lateinit var elEditTextBookAuthor:EditText
    private lateinit var elEditTextBookNationality:EditText
    private lateinit var elEditTextBookDescription:EditText
    private lateinit var elButtonAddBook:Button

    private var db = MyDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        elEditTextBookTitle = findViewById(R.id.EditTextBookTitle)
        elEditTextBookAuthor = findViewById(R.id.EditTextBookAuthor)
        elEditTextBookNationality = findViewById(R.id.EditTextBookNationality)
        elEditTextBookDescription = findViewById(R.id.EditTextBookDescription)
        elButtonAddBook = findViewById(R.id.ButtonAddBook)

        elButtonAddBook.setOnClickListener {
            val inputReq = getInputs()
            val res = db.tableBook.addOne(inputReq)

            if (res.toInt() == -1) {
                Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Added Book Successfully !", Toast.LENGTH_SHORT)
                    .show()
                resetInputs()
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

    private fun resetInputs() {
        elEditTextBookTitle.setText("")
        elEditTextBookAuthor.setText("")
        elEditTextBookNationality.setText("")
        elEditTextBookDescription.setText("")
    }
}