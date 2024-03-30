package com.example.myminebooker.models

class Playlist (
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return "$id. $name"
    }
}