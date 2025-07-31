package com.example.spring_boot

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.awt.PageAttributes


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var library: Library

    @Test
    fun `post one rental object`() {
        // 1. Rentals initial abrufen (z. B. 0)
        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        println("get done ----------------------------------------------------------")
        // 2. Neuen Rental posten
        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        println("Post done -------------------------------------------------------------------------")

        // 3. Rentals nochmal abrufen, diesmal sollte es 1 sein
        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }

        println("second get done ----------------------------------------------------------------------------------")
    }
}
