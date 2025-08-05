package com.example.spring_boot

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
data class User(
    @Id
    val id: Int = 0,

    ) {

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var rentedBooks: MutableList<Rental> = mutableListOf()
}
