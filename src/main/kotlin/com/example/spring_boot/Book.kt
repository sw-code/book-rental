package com.example.spring_boot

data class Book(
    val title: String,
    val id: Int
)

{
    fun getName(): String{
        return title
    }
}