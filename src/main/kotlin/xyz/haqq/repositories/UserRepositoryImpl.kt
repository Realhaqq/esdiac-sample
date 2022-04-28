package xyz.haqq.repositories

import xyz.haqq.pojos.CreateUserParams
import xyz.haqq.pojos.LoginUserParams
import xyz.haqq.security.JwtConfig
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


                val token = JwtConfig.instance.generateAccessToken(user.id)

                user.authToken = token
                BaseResponse.SuccessResponse(data = user, message = "User registered successfully")
            } else {
                BaseResponse.ErrorResponse()
            }
        }

    }

    override suspend fun loginUser(params: LoginUserParams): BaseResponse<Any> {
        val user = userService.loginUser(params)
        println("user $user")
        return if (user != null) {
            val token = JwtConfig.instance.generateAccessToken(user.id)
            user.authToken = token
            BaseResponse.SuccessResponse(data = user, message = "Login Success")
        } else {
            BaseResponse.ErrorResponse("Invalid email or password")
        }

    }

    private suspend fun isEmailExist(email: String): Boolean {
       return userService.findUserByEmail(email) != null
    }
}