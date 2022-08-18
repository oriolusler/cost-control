package com.oriolsoler.costcontroler.integration.helper.repository


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class CostRepositoryForTest(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {
    fun truncate() {
        val sql = "TRUNCATE COST_SHARE, COST"
        val params = MapSqlParameterSource()
        namedParameterJdbcTemplate.update(sql, params)
    }
}