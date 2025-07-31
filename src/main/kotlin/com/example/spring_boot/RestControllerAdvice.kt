package com.example.spring_boot

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException::class)
    fun handleNotFound(ex: BookNotFoundException): ResponseEntity<ProblemDetail> {
        val problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        problem.title = "Book not found"
        problem.detail = ex.message
        return ResponseEntity(problem, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BookNotAvailableRentException::class)
    fun handleAlreadyRented(ex: BookNotAvailableRentException): ResponseEntity<ProblemDetail> {
        val problem = ProblemDetail.forStatus(HttpStatus.CONFLICT)
        problem.title = "Book already rented"
        problem.detail = ex.message
        return ResponseEntity(problem, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BookNotAvailableReturnException::class)
    fun handleNotYetRented(ex: BookNotAvailableReturnException): ResponseEntity<ProblemDetail> {
        val problem = ProblemDetail.forStatus(HttpStatus.CONFLICT)
        problem.title = "Book not yet rented"
        problem.detail = ex.message
        return ResponseEntity(problem, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ProblemDetail> {
        val problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        problem.title = "Unexpected error"
        problem.detail = ex.message ?: "Unknown error"
        return ResponseEntity(problem, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}