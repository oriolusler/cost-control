package com.oriolsoler.costcontroler.integration.helper.repository


import com.oriolsoler.costcontroler.domain.Cost
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder

class CostRepositoryForTest(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun truncate() {
        val sql = "TRUNCATE COST_SHARE, COST"
        val params = MapSqlParameterSource()
        namedParameterJdbcTemplate.update(sql, params)
    }

    fun register(cost: Cost) {
        val sql = """
            |INSERT INTO cost (date, description, category, subcategory, comment, amount, username, identifier)
            |VALUES (:date, :description, :category, :subcategory, :comment, :amount, :username, :identifier)
        """.trimMargin()

        val params = MapSqlParameterSource()
        params.addValue("date", cost.date)
        params.addValue("description", cost.description!!.value)
        params.addValue("category", cost.category?.name)
        params.addValue("subcategory", cost.subcategory?.name)
        params.addValue("comment", cost.comment)
        params.addValue("amount", cost.amount)
        params.addValue("username", cost.username)
        params.addValue("identifier", cost.costIdentifier.value)

        val keyHolder = GeneratedKeyHolder()

        namedParameterJdbcTemplate.update(sql, params, keyHolder)

    }
}