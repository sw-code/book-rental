package com.example.spring_boot

class BookNotAvailableRentException(id: Int) : RuntimeException("Book with ID $id is already rented")
class BookNotAvailableReturnException(id: Int) : RuntimeException("Book with ID $id is not yet rented")
class UserNotFoundException(id: Int) : RuntimeException("User with ID $id not found in Users")
class UserAlreadyExistsException(id: Int) : RuntimeException("User with ID $id already exists")

class BookNotFoundException(id: Int) : RuntimeException("Book with ID $id not found in available books")
