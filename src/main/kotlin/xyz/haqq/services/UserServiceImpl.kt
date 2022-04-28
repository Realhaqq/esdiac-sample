package xyz.haqq.services

import xyz.haqq.db.DatabaseFactory.dbQuery
import xyz.haqq.entities.UserTable
import xyz.haqq.pojos.CreateUserParams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import xyz.haqq.models.UserModel

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