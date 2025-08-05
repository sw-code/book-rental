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
    lateinit var libraryRepository: LibraryRepository

    @Autowired
    lateinit var  librarySetUp: LibrarySetUp

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var rentalRepository: RentalRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup() {
        libraryRepository.deleteAll()
        rentalRepository.deleteAll()
        bookRepository.deleteAll()
        userRepository.deleteAll()

        librarySetUp.librarySet()


    }

    @Test
    fun `should add user if not exists`() {
        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": "1"}"""
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `post one rental object`() {
        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 2 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 2, "userId": 2 }"""
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
        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 3 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 3, "userId": 3 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }

        mockMvc.post("/library/returnBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 3, "userId": 3 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }

    }


    @Test
    fun `post return book object, one still remaining`() {
        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 4 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(0))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 4, "userId": 4 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(1))
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 5, "userId": 4 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/returnBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 4, "userId": 4 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/rentals").andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Int>(2))
        }
    }


    @Test
    fun `reminder fee correctly(reminder fee of 0)`() {
        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 5 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 6, "userId": 5 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/user/5/fees").andExpect {
            status { isOk() }
            content { string("0") }
        }

        val test: Int

    }

    @Test
    fun `should return all books`(){
        mockMvc.get("/library/books").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(10)) }
        }
    }

    @Test
    fun `should return all books currently available books(2)`(){
        mockMvc.get("/library/books/current").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(10)) }
        }
    }

    @Test
    fun `should return all books currently available books(8)`(){

        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 6 }"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 7, "userId": 6 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 8, "userId": 6 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/books/current").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(8)) }
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

        mockMvc.post("/library/addUser") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "userId": 7 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 9, "userId": 7 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.post("/library/rentBook") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "bookId": 10, "userId": 7 }"""
        }.andExpect {
            status { isOk() }
        }


        mockMvc.get("/library/books/currentNot").andExpect {
            status {  {isOk()}
                jsonPath("$", hasSize<Int>(2)) }
        }
    }
}
