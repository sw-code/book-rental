package com.example.spring_boot

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LibraryController {

    lateinit var library: Library

    init {
        librarySet()
    }

    fun librarySet() {
        val books = listOf(Book("Kotlin in action", 1), Book("Jamie Oliver", 10))
        library = Library(books, books.toMutableList())
    }

    @GetMapping("/rentals")
    fun getAllRentals(): ResponseEntity<List<Rental>> {
        return ResponseEntity.ok(library.rentedBooks)
    }

    @PostMapping("/library/rentBook")
    fun rentBook(@RequestBody request: BorrowRequest): ResponseEntity<*> {
        val bookId = request.bookId.toInt()
        val userId = request.userId.toInt()
        val rental = library.rentBook(bookId, userId)
        return ResponseEntity.ok(rental)
    }

    @PostMapping("/library/returnBook")
    fun returnBook(@RequestBody request: BorrowRequest): ResponseEntity<*>{
        val bookId = request.bookId.toInt()
        val rental = library.returnBook(bookId)
        return ResponseEntity.ok(rental)
    }
}