package com.example.spring_boot

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Book(
    val title: String,
    @Id
    val id: Int,
)