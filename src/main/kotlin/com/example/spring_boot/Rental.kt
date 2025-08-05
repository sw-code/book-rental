package com.example.spring_boot

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

@Entity
class Rental(
    @Id
    var id: UUID = UUID.randomUUID(),

    val bookId: Int,

    @ManyToOne
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

    fun calculateFee(): Int{
        val days = ChronoUnit.DAYS.between(toReturnDate, returnedDate)
        if(days > 0){
            return days.toInt()
        }
        return 0
    }
}