package com.example.spring_boot

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

@Entity
class Library(
    @Id
    val id: UUID = UUID.randomUUID(),
    @OneToMany(cascade = [CascadeType.ALL])
    val books: List<Book>,
    @OneToMany(cascade = [CascadeType.ALL])
    var currentBooks: MutableList<Book>
) {
    @OneToMany(cascade = [CascadeType.ALL])
    var users = mutableListOf<User>()
    @OneToMany(cascade = [CascadeType.ALL])
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

    fun rentBook(idInput: Int, userId: Int): Rental {
        val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser != -1) {

            if (books.any { it.id == idInput }) {

                val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

                if (indexOfBook != -1) {

                    rentedBooks.add(Rental(bookId = idInput, user = users[indexUser]))

                    val rental = rentedBooks[rentedBooks.size - 1]
                    rental.info()

                    users[indexUser].rentedBooks.add(rental)

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


    fun returnBook(idInput: Int, userId: Int): Rental {

         val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser != -1) {

            if (books.any { it.id == idInput }) {

                val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

                if (indexOfBook == -1) {
                    val indexOfBook = books.indexOfFirst { it.id == idInput }

                    currentBooks.add(books[indexOfBook])

                    val indexOfRental = rentedBooks.indexOfFirst { it.bookId == idInput }

                    rentedBooks[indexOfRental].returnedDate = LocalDate.now()

                    val feeFromUser = users[indexUser].rentedBooks[indexOfRental].calculateFee()

                    val rental = rentedBooks[indexOfRental]

                    // rentedBooks.removeAt(indexOfRental)

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


    fun calculateFeeFromUser(userId: Int): Int {

        val indexUser = users.indexOfFirst {it.id == userId}

        if(indexUser == -1) {
            throw UserNotFoundException(userId)
        }


        val user = users[indexUser]
        // val feeFromUser = user.rentedBooks.sumOf { it.calculateFee() }

        var feeFromUser = 0
        for(i in user.rentedBooks){
            feeFromUser += i.calculateFee()
        }

        println("the total balance user $userId has to pay is $feeFromUser")
        return feeFromUser
    }

}