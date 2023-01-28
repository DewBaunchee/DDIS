package by.varyvoda.bankapp.domain.repository.connection

import java.sql.Connection
import java.sql.DriverManager

class ConnectionProvider private constructor() {

    companion object {

        val INSTANCE = ConnectionProvider()

        init {
            Class.forName("org.postgresql.Driver")
        }
    }

    fun getConnection(): Connection {
        return get("localhost", 5433, "bank", "postgres", "1")
    }

    private fun get(host: String, port: Int, database: String, username: String, password: String): Connection {
        return DriverManager.getConnection("jdbc:postgresql://$host:$port/$database", username, password)
    }
}