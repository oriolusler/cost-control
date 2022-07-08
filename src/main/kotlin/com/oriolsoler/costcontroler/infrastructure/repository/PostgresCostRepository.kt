package com.oriolsoler.costcontroler.infrastructure.repository

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class PostgresCostRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : CostRepository {
    override fun register(cost: Cost) {
        val sql = """
            |INSERT INTO cost (date, description, category, subcategory, comment, amount)
            |VALUES (:date, :description, :category, :subcategory, :comment, :amount)
        """.trimMargin()

        val params = MapSqlParameterSource()
        params.addValue("date", cost.date)
        params.addValue("description", cost.description.value)
        params.addValue("category", cost.category)
        params.addValue("subcategory", cost.subcategory)
        params.addValue("comment", cost.comment)
        params.addValue("amount", cost.amount)

        namedParameterJdbcTemplate.update(sql, params)
    }

    override fun find(): List<Cost> {
        val sql = """
            SELECT * FROM COST
            """.trimIndent()
        val params = MapSqlParameterSource()
        return namedParameterJdbcTemplate.query(sql, params, mapTo())
    }

    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        Cost(
            rs.getDate("date").toLocalDate(),
            Description(rs.getString("description")),
            rs.getString("category"),
            rs.getString("subcategory"),
            rs.getString("comment"),
            rs.getDouble("amount")
        )
    }
}
