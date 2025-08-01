package com.example.spring_boot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate


@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
	// start()
}

