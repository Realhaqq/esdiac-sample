package xyz.haqq.pojos

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserParams (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val country: String
)