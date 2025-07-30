package com.example.spring_boot

import org.springframework.cglib.core.Local
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var rental = mutableListOf<Rental>()

    fun rentBook(idInput: Int, user: Int): Boolean{  // returns true if the rental was successful otherwise false

        if(books.any{ it.id == idInput }){

            val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

            if(indexOfBook != -1){

                rental.add(Rental(idInput, user))
                rental[rental.size - 1].info()

                currentBooks.removeAt(indexOfBook)

                return true
            }
            else{
                println("book is already rented")
            }
        }
        else{
            println("index out of bounds, the submitted index is out of range of the Available books")
        }
        return false
    }


    fun returnBook(idInput: Int): Boolean{  // returns true if the rental was successful otherwise false

        if(books.any{ it.id == idInput }){

            val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

            if(indexOfBook == -1){
                val indexOfBook = books.indexOfFirst { it.id == idInput }

                currentBooks.add(books[indexOfBook])

                val indexOfRental = rental.indexOfFirst { it.bookId == idInput }

                rental[indexOfRental].returnedDate = LocalDate.now()
                reminderFeeCalculation(rental[indexOfRental], rental[indexOfRental].returnedDate!!)

                rental.removeAt(indexOfRental)
                return true

            }
            else{
                println("book is not rented yet")
            }
        }
        else{
            println("index out of bounds, the submitted index is out of range of the Available books")
        }
        return false
    }


    fun calculateFeeFromUser(user: Int):Int{
        var feeFromUser = 0

        val matchingIndices = rental
            .mapIndexed { index, rental -> if (rental.userId == user) index else null }
            .filterNotNull()

        for(i in matchingIndices){
            feeFromUser += reminderFeeCalculation(rental[i], LocalDate.now())
        }

        println("the total balance user $user has to pay is $feeFromUser")
        return feeFromUser
    }




    fun reminderFeeCalculation(rentalOrder: Rental, date: LocalDate): Int{
        val daysBetween = ChronoUnit.DAYS.between(date, rentalOrder.loanDate)

        if(daysBetween > 14){
            val reminderFee = (daysBetween - 14).toInt()
//            println("the book was returned outside of the 14 days time span, for each day above that threshold we will charge 1€")
//            println("in your case it were $daysBetween which leaves a total of $reminderFee€")
            return reminderFee
        }

//        println("the book was returned on time, very well")
        return 0
    }

}