package com.oriolsoler.costcontroler.integration.helper.repository

import com.oriolsoler.costcontroler.domain.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class UserRepositoryForTest(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun findBy(username: String): User? {
        val sql = "SELECT * FROM USERS WHERE username = :username"

        val params = MapSqlParameterSource()
        params.addValue("username", username)

        return namedParameterJdbcTemplate.query(sql, params, mapTo()).firstOrNull()
    }

    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        User(
            rs.getString("username"),
            rs.getString("password")
        )
    }
}