package com.example.spring_boot

class Library(
    val books: List<Book>,
    var currentBooks: MutableList<Book>
) {

    var rental = mutableListOf<Rental>()

    fun rentBook(idInput: Int): Boolean{  // returns true if the rental was successful otherwise false

        val indexOfBook = findBookInList(currentBooks) {it.id == idInput}

        if(indexOfBook != -1){

            if(findBookInList(currentBooks){it == currentBooks[indexOfBook]} != -1){

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
            println("index out of bounds, the submitted index is out of range of the currently Available books")
        }
        return false
    }

    private fun <T> findBookInList(toCheckList: List<T>, match: (T) -> Boolean): Int {
        for (i in 0..toCheckList.size - 1) {
            if (match(toCheckList[i])) {
                return i
            }
        }
        return -1
    }


}