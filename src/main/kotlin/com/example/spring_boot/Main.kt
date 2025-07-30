package com.example.spring_boot

private val books = listOf(Book("Kotlin in action", 1), Book("Jamie Oliver", 10))

private val library = Library(books, books.toMutableList())

fun main(args: Array<String>) {
	start()
}

fun start(){
	bookInfos()

	callRentBook(){library.rentBook(1)}

	bookInfos()

	callRentBook(){library.rentBook(1)}
}


fun callRentBook(match: (Int) ->  Boolean){
	val i = gainInput()
	if (i != -1) {
		library.rentBook(i)
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