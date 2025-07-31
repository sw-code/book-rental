package com.example.spring_boot

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var rentedBooks = mutableListOf<Rental>()

    fun rentBook(idInput: Int, user: Int): Rental {  // returns true if the rental was successful otherwise false

        if (books.any { it.id == idInput }) {

            val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

            if (indexOfBook != -1) {

                rentedBooks.add(Rental(idInput, user))

                val rental = rentedBooks[rentedBooks.size - 1]
                rental.info()

                currentBooks.removeAt(indexOfBook)

                return rental
            } else {
                throw BookNotAvailableRentException(idInput)
            }
        } else {
            throw BookNotFoundException(idInput)
        }
    }


    fun returnBook(idInput: Int): Rental {  // returns true if the rental was successful otherwise false

        if (books.any { it.id == idInput }) {

            val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

            if (indexOfBook == -1) {
                val indexOfBook = books.indexOfFirst { it.id == idInput }

                currentBooks.add(books[indexOfBook])

                val indexOfRental = rentedBooks.indexOfFirst { it.bookId == idInput }

                rentedBooks[indexOfRental].returnedDate = LocalDate.now()
                reminderFeeCalculation(rentedBooks[indexOfRental], rentedBooks[indexOfRental].returnedDate!!)

                val rental = rentedBooks[indexOfRental]

                rentedBooks.removeAt(indexOfRental)

                return rental

            } else {
                throw BookNotAvailableReturnException(idInput)
            }
        } else {
            throw BookNotFoundException(idInput)
        }
    }


    fun calculateFeeFromUser(user: Int, date: LocalDate): Int {
        var feeFromUser = 0

        val matchingIndices = rentedBooks
            .mapIndexed { index, rental -> if (rental.userId == user) index else null }
            .filterNotNull()

        for (i in matchingIndices) {
            feeFromUser += reminderFeeCalculation(rentedBooks[i], date)
        }

        println("the total balance user $user has to pay is $feeFromUser")
        return feeFromUser
    }


    private fun reminderFeeCalculation(rentalOrder: Rental, date: LocalDate): Int {
        val daysBetween = ChronoUnit.DAYS.between(rentalOrder.loanDate, date)

        if (daysBetween > 14) {
            val reminderFee = (daysBetween - 14).toInt()
//            println("the book was returned outside of the 14 days time span, for each day above that threshold we will charge 1€")
//            println("in your case it were $daysBetween which leaves a total of $reminderFee€")
            return reminderFee
        }

//        println("the book was returned on time, very well")
        return 0
    }

}