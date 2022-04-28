package xyz.haqq.models

data class UserModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String,
    var authToken: String? = null,
    val createdAt: String
)
