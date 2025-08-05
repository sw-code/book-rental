package com.example.spring_boot

import org.springframework.data.jpa.repository.JpaRepository

interface RentalRepository : JpaRepository<Rental, Long>{
    fun findAllByReturnedDateIsNull(): List<Rental>

    }
