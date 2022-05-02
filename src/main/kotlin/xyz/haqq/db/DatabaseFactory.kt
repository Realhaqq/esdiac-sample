package xyz.haqq.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.haqq.entities.UserTable

object DatabaseFactory {

    fun init (){
        Database.connect(hikari())

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
        }
    }

     private fun hikari(): HikariDataSource {
         val config = HikariConfig()
         config.driverClassName = "com.mysql.cj.jdbc.Driver"
         config.jdbcUrl = "jdbc:mysql://localhost:3306/esdiacDB?serverTimezone=UTC"
         config.username = "root"
         config.password = ""
         config.maximumPoolSize = 3
         config.isAutoCommit = false
         config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
         config.validate()
         return HikariDataSource(config)
     }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }
}