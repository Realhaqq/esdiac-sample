package xyz.haqq.services

import xyz.haqq.models.UserModel
import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.pojos.LoginUserParams

interface UserService {
    suspend fun findUserByEmail(email: String): UserModel?
    suspend fun registerUser(params: CreateUserParams): UserModel?
    suspend fun loginUser(params: LoginUserParams): UserModel?
}