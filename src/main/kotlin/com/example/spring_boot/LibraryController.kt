package com.example.spring_boot

import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@Transactional
class LibraryController(
    private val userRepository: UserRepository,
    private val libraryRepository: LibraryRepository,
    private val rentalRepository: RentalRepository
) {


    @GetMapping("/rentals")
    fun getAllRentals(): ResponseEntity<List<Rental>> {
        return ResponseEntity.ok(rentalRepository.findAll())
    }

    @GetMapping("/library/rentals/current")
    fun getCurrentRentals(): ResponseEntity<List<Rental>> {
        val rentals = rentalRepository.findAllByReturnedDateIsNull()
        return ResponseEntity.ok(rentals)
    }

    @PostMapping("/library/rentBook")
    fun rentBook(@RequestBody request: BorrowRequest): ResponseEntity<*> {
        val bookId = request.bookId.toInt()
        val userId = request.userId.toInt()

        // val book = libraryRepository.firstLibrary().books[libraryRepository.firstLibrary().books.indexOfFirst { it.id == userId }]

        val rental = libraryRepository.firstLibrary().rentBook(bookId, userId)
        return ResponseEntity.ok(rental)
    }


    @PostMapping("/library/returnBook")
    fun returnBook(@RequestBody request: BorrowRequest): ResponseEntity<*>{
        val bookId = request.bookId.toInt()
        val userId = request.userId.toInt()
        val rental = libraryRepository.firstLibrary().returnBook(bookId, userId)
        return ResponseEntity.ok(rental)
    }

    @PostMapping("/library/addUser")
    fun addUser(@RequestBody request: BorrowRequest): ResponseEntity<User> {
        val userId = request.userId.toInt()
        val user = libraryRepository.firstLibrary().addUser(userId)

        if (!userRepository.existsById(userId.toLong() )) {
            userRepository.save(user)
        }

        return ResponseEntity.ok(user)
    }

    @GetMapping("/users")
    fun getUsers(): List<User> = userRepository.findAll()


    @GetMapping("/library/user/{id}/fees")
    fun displayReminderFee(@PathVariable id: Long): ResponseEntity<Int>{
        val reminderFeeUser = libraryRepository.firstLibrary().calculateFeeFromUser(id.toInt())
        return ResponseEntity.ok(reminderFeeUser)
    }

    @GetMapping("/library/books")
    fun displayBooks(): ResponseEntity<List<Book>>{
        return ResponseEntity.ok(libraryRepository.firstLibrary().books)
    }

    @GetMapping("/library/books/current")
    fun displayBooksCurrent(): ResponseEntity<List<Book>>{
        return ResponseEntity.ok(libraryRepository.firstLibrary().currentBooks)
    }

    @GetMapping("/library/books/currentNot")
    fun displayBooksCurrentNot(): ResponseEntity<List<Book>>{

        var booksCurrentNot =  mutableListOf<Book>()

        for(i in libraryRepository.firstLibrary().books){
            if(!libraryRepository.firstLibrary().currentBooks.contains(i)){
                booksCurrentNot.add(i)
            }
        }

        return ResponseEntity.ok(booksCurrentNot)
    }



}