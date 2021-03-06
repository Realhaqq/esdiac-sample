package xyz.haqq.repositories

import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.pojos.LoginUserParams
import xyz.haqq.utils.BaseResponse

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
    suspend fun loginUser(params: LoginUserParams): BaseResponse<Any>
}