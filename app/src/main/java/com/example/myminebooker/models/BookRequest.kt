package com.example.myminebooker.models

data class BookRequest (
    val title:String,
    val author:String?,
    val nationality:String?,
    val description:String?
)