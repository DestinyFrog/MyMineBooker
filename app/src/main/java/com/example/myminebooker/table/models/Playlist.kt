package com.example.myminebooker.table.models

class Playlist (
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return "$id. $name"
    }
}