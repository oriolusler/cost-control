package com.oriolsoler.costcontroler.infrastructure.repository.view

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.math.BigDecimal
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP
import java.sql.ResultSet


class PostgresPendingSharedCostViewRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun findBy(username: String): List<PendingSharedCostViewDto> {
        val sql = """
            SELECT description, identifier, cs.amount as amount, debtor
            FROM cost
            LEFT JOIN cost_share cs on cost.id = cs.cost
            WHERE ispaid is false
            AND username = :username;
            """.trimIndent()
        val params = MapSqlParameterSource()
        params.addValue("username", username)
        return namedParameterJdbcTemplate.query(sql, params, mapTo())
    }


    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        PendingSharedCostViewDto(
            rs.getString("description"),
            rs.getString("identifier"),
            rs.getBigDecimal("amount"),
            rs.getString("debtor")
        )
    }
}

data class PendingSharedCostViewDto(
    val description: String,
    val identifier: String,
    var amount: BigDecimal,
    val debtor: String
) {
    init {
       amount = amount.setScale(2, HALF_UP)
    }
}
