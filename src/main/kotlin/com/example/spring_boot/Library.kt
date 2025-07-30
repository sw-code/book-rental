package com.example.spring_boot

class Library {
    val books = listOf<Book>(Book("Kotlin in action", 1), Book("Jamie Oliver", 2))
    var rental =listOf<Rental>()

    fun start(){
        displayAllBookTitles()
    }

    fun displayAllBookTitles(){
        for(i in 0..books.size){
            books[]
        }
    }



}