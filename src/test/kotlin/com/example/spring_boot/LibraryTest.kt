package com.example.spring_boot

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue

class LibraryTest {

    val books = listOf(Book("Kotlin in action", 1), Book("Jamie Oliver", 10), Book("Die Geschichte des Swaglords", 11),Book("Hausarbeit eines BWL studenten", 8))
    var currentBooks = books.toMutableList()

    val library = Library(books, currentBooks)

    @Test
    fun `should rent a book`(){
        val pickedId = 1

        assertTrue(library.rentBook(pickedId, 1))
    }

    @Test
    fun `should rent a book 2`(){
        val pickedId = 10

        assertTrue(library.rentBook(pickedId, 1))
    }

    @Test
    fun `should book the entire book collection`(){
        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)

        assertTrue(library.rentBook(8, 1))
    }

    @Test
    fun `the book is already rented`(){
        val pickedId = 1

        library.rentBook(pickedId, 1)
        assertFalse(library.rentBook(pickedId, 1))
    }


    @Test
    fun `the book id is out of bounds for renting`(){
        val pickedId = 100

        library.rentBook(pickedId, 1)
        assertFalse(library.rentBook(pickedId, 1))
    }



    @Test
    fun `should return a book`(){
        val pickedId = 1

        library.rentBook(pickedId, 1)
        assertTrue { library.returnBook(pickedId) }
    }


    @Test
    fun `should return a book 2`(){
        val pickedId = 10

        library.rentBook(pickedId, 1)
        assertTrue { library.returnBook(pickedId) }
    }


    @Test
    fun `should return the entire book collection`(){
        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)
        library.rentBook(8, 1)

        library.returnBook(1)
        library.returnBook(10)
        library.returnBook(11)
        assertTrue { library.returnBook(8) }
    }


    @Test
    fun `the book is not rented`(){
        val pickedId = 1

        library.returnBook(pickedId)
        assertFalse { library.returnBook(pickedId) }
    }



    @Test
    fun `the book id is out of bounds for return`(){
        val pickedId = 100

        library.returnBook(pickedId)
        assertFalse { library.returnBook(pickedId) }
    }





    @Test
    fun `loan date correctly safed`(){
        library.rentBook(1, 1)

        assertEquals(library.rentedBooks[0].loanDate, LocalDate.now())
    }

    @Test
    fun `to return date correctly safed`(){
        library.rentBook(1, 1)

        assertEquals(library.rentedBooks[0].loanDate.plusDays(14), library.rentedBooks[0].toReturnDate)
    }


    @Test
    fun `should not calculate a reminder fee(on time given back)`(){
        library.rentBook(1, 1)

        assertEquals(library.calculateFeeFromUser(1, LocalDate.now()), 0)
    }

    @Test
    fun `should not calculate a reminder fee(no books rented)`(){
        assertEquals(library.calculateFeeFromUser(1, LocalDate.now()), 0)
    }

    @Test
    fun `should calculate a reminder fee`(){
        library.rentBook(1, 1)

        val date = LocalDate.now().plusDays(20)

        assertEquals(library.calculateFeeFromUser(1, date), 6)
    }


    @Test
    fun `reminder fee for the hole book collection`(){
        library.rentBook(1, 1)
        library.rentBook(10, 1)
        library.rentBook(11, 1)
        library.rentBook(8, 1)

        val date = LocalDate.now().plusDays(20)

        assertEquals(library.calculateFeeFromUser(1, date),24)
    }


}