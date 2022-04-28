package xyz.haqq.repositories

import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.services.UserService
import xyz.haqq.utils.BaseResponse

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): BaseResponse<Any> {

        return if (isEmailExist(params.email)) {
            BaseResponse.ErrorResponse(
                "Email already exist"
            )
        }else{
            val user = userService.registerUser(params)
            if (user != null) {

                // TODO: generate token
                BaseResponse.SuccessResponse(data = user)
            } else {
                BaseResponse.ErrorResponse()
            }
        }

    }

    override suspend fun loginUser(email: String, password: String): BaseResponse<Any> {
        TODO("Not yet implemented")
    }

    private suspend fun isEmailExist(email: String): Boolean {
       return userService.findUserByEmail(email) != null
    }
}