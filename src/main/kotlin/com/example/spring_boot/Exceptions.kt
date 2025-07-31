package com.example.spring_boot

class BookNotAvailableRentException(id: Int) : RuntimeException("Book with ID $id is already rented")
class BookNotAvailableReturnException(id: Int) : RuntimeException("Book with ID $id is not yet rented")
class BookNotFoundException(id: Int) : RuntimeException("Book with ID $id not found in available books")
