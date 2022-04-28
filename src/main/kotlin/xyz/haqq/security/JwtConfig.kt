package xyz.haqq.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JwtConfig private constructor(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun generateAccessToken(id: Int): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim("id", id )
        .sign(algorithm)


    companion object {
        private const val ISSUER = "haqq"
        private const val AUDIENCE = "haqq"

        const val CLAIM = "id"

        lateinit var instance: JwtConfig
        private set



        fun init(secret: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig(secret)
                }
            }
        }
    }
}