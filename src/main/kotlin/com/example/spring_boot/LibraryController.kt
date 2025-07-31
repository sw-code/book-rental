package com.example.spring_boot

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


private val LibraryController.id: String
    get() {
        TODO()
    }

@RestController
class LibraryController {


    @GetMapping("/rentals")
    fun getAllRentals(): ResponseEntity<List<Rental>> {
        return ResponseEntity.ok(library.rentedBooks)
    }

    @PostMapping("/library/rentBook")
    fun rentBook(@RequestBody request: BorrowRequest): ResponseEntity<*> {
        val bookId = request.bookId.toInt()
        val rental = library.rentBook(bookId, 1)
        return ResponseEntity.ok(rental)
    }
}