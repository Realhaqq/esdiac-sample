package xyz.haqq.security

import io.ktor.auth.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*

fun Application.configureSecurity() {
    JwtConfig.init("haqq")
    install(Authentication){

         jwt {
             verifier(JwtConfig.instance.verifier )

             validate {
                 val claims = it.payload.getClaim(JwtConfig.CLAIM)

                 if (claims != null) {
                     UserIdPrinciple(claims.asInt())
                 }else{
                     null
                 }
             }
         }
    }
}