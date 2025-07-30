package com.example.spring_boot

import java.time.LocalDate

class Rental(
    val bookId: Int,
    val userId: Int,

    val loanDate: LocalDate = LocalDate.now(),
    var returnedDate: LocalDate? = null,
    val toReturnDate: LocalDate = loanDate.plusDays(14)
)
{
    fun info(){
        println("bookId, $bookId")
        println("userId, $userId")
        println("loanDate, $loanDate")
    }
}