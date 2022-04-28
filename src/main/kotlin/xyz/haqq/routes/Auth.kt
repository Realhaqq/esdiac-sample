package xyz.haqq.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.pojos.LoginUserParams
import xyz.haqq.repositories.UserRepository

fun Application.authRoutes(repository: UserRepository) {
  routing {
      route("/"){
          get("/"){
              call.respondText("Hello World")
          }
      }

   route("/api/v1/auth"){
    post("/register") {
         val user = call.receive<CreateUserParams>()

         val result = repository.registerUser(user)
         call.respond(result.statusCode,result)
    }

    post("/login") {
        val user = call.receive<LoginUserParams>()

        val result = repository.loginUser(user)
        call.respond(result.statusCode,result)
    }
   }
  }

}