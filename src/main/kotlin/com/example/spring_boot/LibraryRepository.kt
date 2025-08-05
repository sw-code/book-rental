package com.example.spring_boot

import org.springframework.data.jpa.repository.JpaRepository

interface LibraryRepository : JpaRepository<Library, Long>

fun LibraryRepository.firstLibrary(): Library {
    return this.findAll().first()
}