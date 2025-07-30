package com.example.spring_boot

val books = listOf(Book("Kotlin in action", 1), Book("Jamie Oliver", 2))
var currentBooks = books.toMutableList()

val library = Library(books, currentBooks)

fun main(args: Array<String>) {
	start()
}

fun start(){
	bookInfos()

	callRentBook()

	bookInfos()

	callRentBook()
}


fun callRentBook(){
	val i = gainInput()
	if (i != -1) {
		library.rentBook(i)
	}
}



fun bookInfos(){
	println("--------------------------------------------------------------------")
	displayBookTitles(books, "All books in our collection: ")
	println("-----------------")
	displayBookTitles(currentBooks, "all currently available books: ")
	println("-----------------")
}

fun displayBookTitles(toCheck: List<Book>, message: String){
	println(message)
	for(i in 0..toCheck.size - 1){
		println(toCheck[i].getName())
	}
}

fun gainInput(): Int{
	print("input bookId to rent a book: ")

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