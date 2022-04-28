package xyz.haqq.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable: Table("users") {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", length = 255)
    val lastName = varchar("last_name", length = 255)
    val email = varchar("email", length = 255)
    val password = varchar("password", length = 255)
    val country = varchar("country", length = 255)
    val createdAt = datetime("created_at").clientDefault { java.time.LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}