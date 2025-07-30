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

        assertTrue(library.rentBook(pickedId))
    }

    @Test
    fun `should rent a book 2`(){
        val pickedId = 10

        assertTrue(library.rentBook(pickedId))
    }

    @Test
    fun `should book the entire book collection`(){
        library.rentBook(1)
        library.rentBook(10)
        library.rentBook(11)

        assertTrue(library.rentBook(8))
    }

    @Test
    fun `the book is already rented`(){
        val pickedId = 1

        library.rentBook(pickedId)
        assertFalse(library.rentBook(pickedId))
    }


    @Test
    fun `the book id is out of bounds for renting`(){
        val pickedId = 100

        library.rentBook(pickedId)
        assertFalse(library.rentBook(pickedId))
    }



    @Test
    fun `should return a book`(){
        val pickedId = 1

        library.rentBook(pickedId)
        assertTrue { library.returnBook(pickedId) }
    }


    @Test
    fun `should return a book 2`(){
        val pickedId = 10

        library.rentBook(pickedId)
        assertTrue { library.returnBook(pickedId) }
    }


    @Test
    fun `should return the entire book collection`(){
        library.rentBook(1)
        library.rentBook(10)
        library.rentBook(11)
        library.rentBook(8)

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
        library.rentBook(1)

        assertEquals(library.rental[0].loanDate, LocalDate.now())
    }

    @Test
    fun `to return date correctly safed`(){
        library.rentBook(1)

        assertEquals(library.rental[0].loanDate.plusDays(14), library.rental[0].toReturnDate)
    }
}