package com.oriolsoler.costcontroler.infrastructure.repository

import com.oriolsoler.costcontroler.domain.User
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresUserRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : UserRepository {
    override fun register(user: User) {
        val userSql = """
            |INSERT INTO users (username, password)
            |VALUES (:username, :password)
        """.trimMargin()

        val authoritySql = """
            |INSERT INTO AUTHORITIES (username, authority)
            |VALUES (:username, :authority)
        """.trimMargin()

        val userParams = MapSqlParameterSource()
        userParams.addValue("username", user.username)
        userParams.addValue("password", user.password)

        val authorityParams = MapSqlParameterSource()
        authorityParams.addValue("username", user.username)
        authorityParams.addValue("authority", "ROLE_USER")

        namedParameterJdbcTemplate.update(userSql, userParams)
        namedParameterJdbcTemplate.update(authoritySql, authorityParams)
    }

    override fun exists(username: String): Boolean {
        val existsSql = """
            SELECT EXISTS(SELECT 1 FROM USERS WHERE username=:username)
        """.trimIndent()

        val existsParams = MapSqlParameterSource()
        existsParams.addValue("username", username)

        return namedParameterJdbcTemplate.queryForObject(existsSql, existsParams, Boolean::class.java) == true
    }
}