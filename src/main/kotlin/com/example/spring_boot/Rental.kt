package com.example.spring_boot

import java.time.LocalDate

class Rental(
    val bookId: Int,
    val user: User,

    val loanDate: LocalDate = LocalDate.now(),
    var returnedDate: LocalDate? = null,
    val toReturnDate: LocalDate = loanDate.plusDays(14)
)
{
    fun info(){
        val tmp = user.id

        println("bookId, $bookId")
        println("userId, $tmp")
        println("loanDate, $loanDate")
    }
}