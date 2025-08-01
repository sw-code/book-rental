package com.example.spring_boot

data class User(
    val id: Int
)
{
    var rentedBooks = mutableListOf<Book>()
}