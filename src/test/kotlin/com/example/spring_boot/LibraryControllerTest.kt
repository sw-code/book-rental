package com.example.spring_boot

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LibraryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var libraryController: LibraryController

    @BeforeEach
    fun setup() {
        libraryController.librarySet()
    }

    @Test
    fun `post one rental object`() {
        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }
    }


    @Test
    fun `post return book object`() {
        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/returnBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

    }


    @Test
    fun `post return book object, one still remaining`() {
        // 1. Rentals initial abrufen (z. B. 0)
        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }
        // 2. Neuen Rental posten
        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 10, "userId": 42 }"""
        }.andExpect {
            status { isOk() }
        }

        println("Post done -------------------------------------------------------------------------")

        mockMvc.post("/library/returnBook") {
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
