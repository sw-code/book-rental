package com.example.spring_boot

data class BorrowRequest(
    val bookId: Long,
    val userId: Long
)