package xyz.haqq.services

import xyz.haqq.models.UserModel
import xyz.haqq.pojos.CreateUserParams

interface UserService {
    suspend fun findUserByEmail(email: String): UserModel?
    suspend fun registerUser(params: CreateUserParams): UserModel?
}