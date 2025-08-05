package com.example.spring_boot

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue

class LibraryTest {

    val books = listOf(Book("Kotlin in action", 1), Book("Swaglord Triology", 2), Book("BWL student life comparison", 3), Book("SQL for beginners", 4), Book("Suicidal, a starter guide", 5), Book("the depressed author", 6), Book("Cooking for people who do not cook Oliver", 7), Book("Psychology of Money", 8), Book("Rich Dad Poor Dad", 9), Book("Jamie Oliver", 10))
    var currentBooks = books.toMutableList()

    val library = Library(books = books, currentBooks = currentBooks)

    @Test
    fun `should rent a book`(){
        val pickedId = 1

        library.addUser(1)

        val result = library.rentBook(pickedId, 1)
        assertEquals(result::class.simpleName, "Rental")
    }

    @Test
    fun `should rent a book 2`(){
        val pickedId = 10

        library.addUser(1)

        val result = library.rentBook(pickedId, 1)
        assertEquals(result::class.simpleName, "Rental")
    }

    @Test
    fun `should book the entire book collection`(){
        library.addUser(1)

        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)

        val result = library.rentBook(8, 1)
        assertEquals(result::class.simpleName, "Rental")
    }

    @Test
    fun `the book is already rented`(){
        val pickedId = 1

        library.addUser(1)

        library.rentBook(pickedId, 1)

        assertThrows<Exception>{
            library.rentBook(pickedId, 1)
        }
    }


    @Test
    fun `the book id is out of bounds for renting`(){
        val pickedId = 100

        library.addUser(1)

        assertThrows<Exception>{
            library.rentBook(pickedId, 1)
        }
    }



    @Test
    fun `should return a book`(){
        val pickedId = 1

        library.addUser(1)

        library.rentBook(pickedId, 1)

        val result = library.returnBook(pickedId, 1)
        assertEquals(result::class.simpleName, "Rental")
    }


    @Test
    fun `should return a book 2`(){
        val pickedId = 10

        library.addUser(1)

        library.rentBook(pickedId, 1)

        val result = library.returnBook(pickedId, 1)
        assertEquals(result::class.simpleName, "Rental")
    }


    @Test
    fun `should return the entire book collection`(){
        library.addUser(1)

        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)
        library.rentBook(8, 1)

        library.returnBook(1, 1)
        library.returnBook(10, 1)
        library.returnBook(11, 1)

        val result = library.returnBook(8, 1)
        assertEquals(result::class.simpleName, "Rental")
    }


    @Test
    fun `the book is not rented`(){
        val pickedId = 1

        library.addUser(1)

        assertThrows<Exception>{
            library.returnBook(pickedId, 1)
        }
    }



    @Test
    fun `the book id is out of bounds for return`(){
        val pickedId = 100

        library.addUser(1)

        assertThrows<Exception>{
            library.returnBook(pickedId, 1)
        }
    }





    @Test
    fun `loan date correctly safed`(){
        library.addUser(1)

        library.rentBook(1, 1)

        assertEquals(library.rentedBooks[0].loanDate, LocalDate.now())
    }

    @Test
    fun `to return date correctly safed`(){
        library.addUser(1)

        library.rentBook(1, 1)

        assertEquals(library.rentedBooks[0].loanDate.plusDays(14), library.rentedBooks[0].toReturnDate)
    }


    @Test
    fun `should not calculate a reminder fee(on time given back)`(){
        library.addUser(1)

        library.rentBook(1, 1)

        assertEquals(library.calculateFeeFromUser(1), 0)
    }

    @Test
    fun `should not calculate a reminder fee(no books rented)`(){
        library.addUser(1)

        assertEquals(library.calculateFeeFromUser(1), 0)
    }

    @Test
    fun `should calculate a reminder fee`(){
        library.addUser(1)

        library.rentBook(1, 1)
        val date = LocalDate.now().plusDays(20)

        assertEquals(library.calculateFeeFromUser(1), 6)
    }


    @Test
    fun `reminder fee for the hole book collection`(){
        library.addUser(1)

        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)
        library.rentBook(8, 1)

        val date = LocalDate.now().plusDays(20)

        assertEquals(library.calculateFeeFromUser(1),24)
    }


}