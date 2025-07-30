package com.example.spring_boot

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class RentalTest {

    @Test
    fun loanDateCorrectlySafed(){
        library.rentBook(1)

        assertEquals(library.rental[0].loanDate, LocalDate.now())
    }

    @Test
    fun loanDateIncorrectlySafed(){
        library.rentBook(1)

        assertNotEquals(library.rental[0].loanDate.plusDays(1), LocalDate.now())
    }

    @Test
    fun toReturnDateCorrectlySafed(){
        library.rentBook(1)

        assertEquals(library.rental[0].loanDate.plusDays(14), library.rental[0].toReturnDate)
    }

    @Test
    fun toReturnDateIncorrectlySafed(){
        library.rentBook(1)

        assertNotEquals(library.rental[0].loanDate.plusDays(14), library.rental[0].toReturnDate.plusDays(1))
    }
}