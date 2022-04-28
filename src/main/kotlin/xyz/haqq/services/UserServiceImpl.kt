package xyz.haqq.services

import org.jetbrains.exposed.sql.*
import xyz.haqq.db.DatabaseFactory.dbQuery
import xyz.haqq.entities.UserTable
import xyz.haqq.pojos.CreateUserParams
import org.jetbrains.exposed.sql.statements.InsertStatement
import xyz.haqq.models.UserModel
import xyz.haqq.pojos.LoginUserParams

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): UserModel? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserTable.insert {
                it[firstName] = params.firstName
                it[lastName] = params.lastName
                it[email] = params.email
                it[password] = params.password
                it[country] = params.country
            }
        }

        return rowToUser(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun loginUser(params: LoginUserParams): UserModel? {
        val user = dbQuery {
            UserTable.select {
                UserTable.email eq params.email and (UserTable.password eq params.password)
            }.map { rowToUser(it) }.singleOrNull()
        }

        return user
    }

    override suspend fun findUserByEmail(email: String): UserModel? {
        val user = dbQuery {
            UserTable.select { UserTable.email.eq(email) }
                .map { rowToUser(it) }.singleOrNull()
        }

        return user
    }


    private fun rowToUser(row: ResultRow): UserModel? {

        return if (row == null) {
            throw IllegalArgumentException("User not found")
        }else UserModel(
            id = row[UserTable.id],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            email = row[UserTable.email],
            country = row[UserTable.country],
            createdAt = row[UserTable.createdAt].toString(),
        )

        }

}