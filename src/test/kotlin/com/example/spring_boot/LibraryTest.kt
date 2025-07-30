package com.example.spring_boot

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class LibraryTest {
    @Test
    fun notYetRentedBook(){
        val pickedId = 1

        assertTrue(library.rentBook(pickedId))
    }

    @Test
    fun alreadyRentedBook(){
        val pickedId = 1

        library.rentBook(pickedId)
        assertFalse(library.rentBook(pickedId))
    }
}