package com.example.spring_boot

import jakarta.transaction.Transactional
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Transactional
class LibrarySetUp(
    private val libraryRepository: LibraryRepository
)
{
    @EventListener(ApplicationStartedEvent::class)
    fun librarySet() {
        if(libraryRepository.count().toInt() == 0) {
            val books = listOf(Book("Kotlin in action", 1), Book("Swaglord Triology", 2), Book("BWL student life comparison", 3), Book("SQL for beginners", 4), Book("Suicidal, a starter guide", 5), Book("the depressed author", 6), Book("Cooking for people who do not cook Oliver", 7), Book("Psychology of Money", 8), Book("Rich Dad Poor Dad", 9), Book("Jamie Oliver", 10))
            val library = Library(books = books, currentBooks = books.toMutableList())
            libraryRepository.save(library)
        }
    }
}
