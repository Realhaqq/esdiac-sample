package xyz.haqq.pojos

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserParams (
    val email: String,
    val password: String
)