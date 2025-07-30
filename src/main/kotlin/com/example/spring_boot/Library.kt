package com.example.spring_boot

class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var rental = mutableListOf<Rental>()

    fun rentBook(idInput: Int): Boolean{  // returns true if the rental was successful otherwise false
        if(idInput > 0 && idInput <= books.size){
            if(currentBooks.contains(books[idInput])){

                rental.add(Rental(idInput, 1))
                rental[rental.size - 1].info()

                currentBooks.removeAt(idInput)

                return true
            }
            else{
                println("book is already rented")
            }
        }
        else{
            println("index out of bounds, the submitted index is out of range")
        }
        return false
    }


}