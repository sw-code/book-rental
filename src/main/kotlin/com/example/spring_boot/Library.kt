package com.example.spring_boot

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var users = mutableListOf<User>()
    var rentedBooks = mutableListOf<Rental>()

    fun addUser(id: Int): User{
        println("new user")
        val contains = users.indexOfFirst { it.id == id } != -1
        if(!contains){
            users.add(User(id))
            return users[users.size - 1]
        }
        else{
            throw UserAlreadyExistsException(id)
        }
    }

    fun test(user: User): String{
        println("test")
        val tester = "user stuff: "+ (users.indexOfFirst {it == user} != 0)
        println(tester)
        return tester
    }

    fun rentBook(idInput: Int, userId: Int): Rental {  // returns true if the rental was successful otherwise false
        val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser != -1) {

            if (books.any { it.id == idInput }) {

                val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

                if (indexOfBook != -1) {

                    rentedBooks.add(Rental(idInput, users[indexUser]))

                    val rental = rentedBooks[rentedBooks.size - 1]
                    rental.info()

                    users[indexUser].rentedBooks.add(books[books.indexOfFirst { it.id == idInput }])

                    currentBooks.removeAt(indexOfBook)

                    return rental
                } else {
                    throw BookNotAvailableRentException(idInput)
                }
            } else {
                throw BookNotFoundException(idInput)
            }
        }
        else{
            throw UserNotFoundException(userId)
        }
    }


    fun returnBook(idInput: Int, userId: Int): Rental {  // returns true if the rental was successful otherwise false

        val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser != -1) {

            if (books.any { it.id == idInput }) {

                val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

                if (indexOfBook == -1) {
                    val indexOfBook = books.indexOfFirst { it.id == idInput }

                    currentBooks.add(books[indexOfBook])

                    users[indexUser].rentedBooks.remove(books[indexOfBook])

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
        else{
            throw UserNotFoundException(userId)
        }
    }


    fun calculateFeeFromUser(userId: Int, date: LocalDate): Int {

        val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser == -1) {
            throw UserNotFoundException(userId)
        }

        var feeFromUser = 0

        for (i in users[indexUser].rentedBooks) {
            feeFromUser += reminderFeeCalculation(rentedBooks[rentedBooks.indexOfFirst { it.bookId == i.id }], date)
        }

        println("the total balance user $userId has to pay is $feeFromUser")
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