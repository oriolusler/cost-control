package com.oriolsoler.costcontroler.infrastructure.repository.view

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.math.BigDecimal
import java.sql.ResultSet
import java.time.LocalDate

class PostgresGetCostsRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun currentMonthFor(username: String): List<CostResponseDto> {
        val sql = """
            SELECT * 
            FROM COST
            WHERE username = :username
            AND  EXTRACT('month' from  date) = :month 
            AND EXTRACT('year' from date) = :year
            """.trimIndent()
        val params = MapSqlParameterSource()
        params.addValue("username", username)

        val now = LocalDate.now()
        params.addValue("month", now.monthValue)
        params.addValue("year", now.year)

        return namedParameterJdbcTemplate.query(sql, params, mapTo())
    }

    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        CostResponseDto(
            rs.getDate("date").toLocalDate(),
            rs.getString("category"),
            rs.getBigDecimal("amount")
        )
    }
}

data class CostResponseDto(val date: LocalDate, val category: String, val amount: BigDecimal)