package xyz.haqq

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import xyz.haqq.db.DatabaseFactory
import xyz.haqq.repositories.UserRepository
import xyz.haqq.repositories.UserRepositoryImpl
import xyz.haqq.routes.authRoutes
import xyz.haqq.services.UserService
import xyz.haqq.services.UserServiceImpl

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()

        install(ContentNegotiation){
            jackson()
        }


        val userService : UserService = UserServiceImpl()
        val userRepository : UserRepository = UserRepositoryImpl(userService)
        authRoutes(userRepository)

    }.start(wait = true)
}