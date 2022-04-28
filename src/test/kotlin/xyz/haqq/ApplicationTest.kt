package xyz.haqq

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.pojos.LoginUserParams

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world", response.bodyAsText())
    }

    @Test
    fun testCreateUserParams() {
        val createUserParams = CreateUserParams("haqq", "samiul", "real@yahoo.com", "12345678", "Nigeria")
        assertEquals("haqq", createUserParams.firstName)
    }


    @Test
    fun testLoginUserParams() {
        val loginUserParams = LoginUserParams("real@yahoo.com", "12345678")
//        assertEquals("haqq", loginUserParams.fistName)
        assert(loginUserParams.email == "" && loginUserParams.password == "")
        // body response

    }


    @Test
    fun testLogin() = testApplication {
        val response = client.post("/api/v1/auth/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Any)
            setBody(listOf("email" to "haqq4peace@gmail.com", "password" to "Samiu987$"))
        }
        assertEquals("", response.bodyAsText())
    }



}