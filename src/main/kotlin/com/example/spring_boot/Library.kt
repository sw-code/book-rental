package com.example.spring_boot

class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var rental = mutableListOf<Rental>()

    fun rentBook(idInput: Int): Boolean{  // returns true if the rental was successful otherwise false

        if(books.any{ it.id == idInput }){

            val indexOfBook = currentBooks.indexOfFirst { it.id == idInput }

            if(indexOfBook != -1){

                rental.add(Rental(idInput, 1))
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

}