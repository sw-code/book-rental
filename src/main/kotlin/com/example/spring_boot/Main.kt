package com.example.spring_boot

import java.time.LocalDate

private val books = listOf(Book("Kotlin in action", 1), Book("Jamie Oliver", 10))

private val library = Library(books, books.toMutableList())

fun main(args: Array<String>) {
	start()
}

fun start(){
	bookInfos()

	callRentBook()

	bookInfos()

	callRentBook()

	bookInfos()

	library.calculateFeeFromUser(1, LocalDate.now())

	callReturnBook()

	bookInfos()

	callReturnBook()

	bookInfos()

	library.calculateFeeFromUser(1, LocalDate.now())
}


fun callRentBook(){
	val i = gainInput("input bookId to rent a book: ")
	if (i != -1) {
		library.rentBook(i, 1)
	}
}

fun callReturnBook(){
	val i = gainInput("input bookId to return your book: ")
	if (i != -1) {
		library.returnBook(i)
	}
}



fun bookInfos(){
	println("--------------------------------------------------------------------")
	displayBookTitles(library.books, "All books in our collection: ")
	println("-----------------")
	displayBookTitles(library.currentBooks, "all currently available books: ")
	println("-----------------")
}

fun displayBookTitles(toCheck: List<Book>, message: String){
	println(message)
	for(i in 0..toCheck.size - 1){
		println(toCheck[i].getName())
	}
}

fun gainInput(message: String): Int{
	print(message)

	val idInput: Int

	try {
		idInput = readLine()!!.toInt()
	}
	catch (e: NumberFormatException){
		println("invalid input, please enter a number")
		return -1
	}
	catch (e: Exception){
		println("something went wrong, try again. Following error occurred: $e")
		return -1
	}

	return idInput
}