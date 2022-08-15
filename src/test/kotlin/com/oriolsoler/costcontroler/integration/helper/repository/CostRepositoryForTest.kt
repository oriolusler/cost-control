package com.oriolsoler.costcontroler.integration.helper.repository


import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class CostRepositoryForTest(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun findBy(description: Description): Cost? {
        val sql = "SELECT * FROM COST WHERE description = :description"

        val params = MapSqlParameterSource()
        params.addValue("description", description.value)

        return namedParameterJdbcTemplate.query(sql, params, mapTo()).firstOrNull()
    }

    fun truncate() {
        val sql = "TRUNCATE COST"
        val params = MapSqlParameterSource()
        namedParameterJdbcTemplate.update(sql, params)
    }

    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        Cost(
            rs.getDate("date").toLocalDate(),
            Description(rs.getString("description")),
            rs.getString("category"),
            rs.getString("subcategory"),
            rs.getString("comment"),
            rs.getBigDecimal("amount"),
            rs.getString("username"),
            rs.getBoolean("is_pending_to_pay"),
            rs.getBigDecimal("pending_to_pay_amount")
        )
    }
}