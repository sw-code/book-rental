package com.example.spring_boot

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


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
        val userId = request.userId.toInt()
        val rental = library.returnBook(bookId, userId)
        return ResponseEntity.ok(rental)
    }

    @PostMapping("/library/addUser/{id}")
    fun addUser(@PathVariable id: Long): ResponseEntity<*>{
        val user = library.addUser(id.toInt())
        return ResponseEntity.ok(user)
    }

    @GetMapping("/library/user/{id}/fees")
    fun displayReminderFee(@PathVariable id: Long): ResponseEntity<Int>{
        val reminderFeeUser = library.calculateFeeFromUser(id.toInt(), LocalDate.now())
        return ResponseEntity.ok(reminderFeeUser)
    }

    @GetMapping("/library/books")
    fun displayBooks(): ResponseEntity<List<Book>>{
        return ResponseEntity.ok(library.books)
    }

    @GetMapping("/library/books/current")
    fun displayBooksCurrent(): ResponseEntity<List<Book>>{
        return ResponseEntity.ok(library.currentBooks)
    }

    @GetMapping("/library/books/currentNot")
    fun displayBooksCurrentNot(): ResponseEntity<List<Book>>{

        var booksCurrentNot =  mutableListOf<Book>()

        for(i in library.books){
            if(!library.currentBooks.contains(i)){
                booksCurrentNot.add(i)
            }
        }

        return ResponseEntity.ok(booksCurrentNot)
    }
}