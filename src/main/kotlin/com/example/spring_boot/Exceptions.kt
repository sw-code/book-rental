package com.example.spring_boot

class BookNotAvailableException(id: Int) : RuntimeException("Book with ID $id is already rented")
class BookNotFoundException(id: Int) : RuntimeException("Book with ID $id not found in available books")
