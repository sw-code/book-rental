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
    fun `create one user`(){
        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

    }

    @Test
    fun `post one rental object`() {
        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
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
        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/returnBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
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
        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 10, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/returnBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }
    }


    @Test
    fun `reminder fee correctly(reminder fee of 0)`() {
        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/user/1/fees").andExpect {
            status { isOk() }
            content { string("0") }
        }


    }

    @Test
    fun `should return all books`(){
        mockMvc.get("/library/books").andExpect {
            status {  {isOk()}
            jsonPath("$", hasSize<Int>(2)) }
        }
    }

    @Test
    fun `should return all books currently available books(2)`(){
        mockMvc.get("/library/books/current").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(2)) }
        }
    }

    @Test
    fun `should return all books currently available books(0)`(){

        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 10, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/books/current").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(0)) }
        }
    }


    @Test
    fun `should return all books not currently available books(0)`(){
        mockMvc.get("/library/books/currentNot").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(0)) }
        }
    }

    @Test
    fun `should return all books not currently available books(2)`(){

        mockMvc.post("/library/addUser/1").andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 1, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 10, "userId": 1 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/books/currentNot").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(2)) }
        }
    }
}
