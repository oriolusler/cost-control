package com.oriolsoler.costcontroler.infrastructure.repository

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.ResultSet


class PostgresCostRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : CostRepository {
    override fun register(cost: Cost) {
        val sql = """
            |INSERT INTO cost (date, description, category, subcategory, comment, amount, username)
            |VALUES (:date, :description, :category, :subcategory, :comment, :amount, :username)
        """.trimMargin()

        val params = MapSqlParameterSource()
        params.addValue("date", cost.date)
        params.addValue("description", cost.description!!.value)
        params.addValue("category", cost.category)
        params.addValue("subcategory", cost.subcategory)
        params.addValue("comment", cost.comment)
        params.addValue("amount", cost.amount)
        params.addValue("username", cost.username)

        val keyHolder = GeneratedKeyHolder()

        namedParameterJdbcTemplate.update(sql, params, keyHolder)

        val newCostId =  keyHolder.keyList[0]["id"] as Int

        insertSharedCostFor(newCostId, cost.shared!!)
    }

    override fun findBy(username: String): List<Cost> {
        val sql = """
            SELECT * 
            FROM COST
            WHERE username = :username
            """.trimIndent()
        val params = MapSqlParameterSource()
        params.addValue("username", username)
        return namedParameterJdbcTemplate.query(sql, params, mapTo())
    }

    override fun insertSharedCostFor(costId: Number, sharedCost: List<SharedCost>) {
        val sql = """
            |INSERT INTO COST_SHARE (cost, amount, isPaid, debtor)
            |VALUES (:cost, :amount, :isPaid, :debtor)
        """.trimMargin()

        sharedCost.forEach {
            val params = MapSqlParameterSource()
            params.addValue("cost", costId)
            params.addValue("amount", it.amount)
            params.addValue("isPaid", it.isPaid)
            params.addValue("debtor", it.debtor)

            namedParameterJdbcTemplate.update(sql, params)
        }
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
            getSharedCosts(rs.getInt("id"))
        )
    }

    private fun getSharedCosts(costId: Int): List<SharedCost> {
        val sql = """
            SELECT * 
            FROM COST_SHARE
            WHERE cost = :cost_id
            """.trimIndent()
        val params = MapSqlParameterSource()
        params.addValue("cost_id", costId)
        return namedParameterJdbcTemplate.query(sql, params, mapToSharedCost())
    }

    private fun mapToSharedCost() = RowMapper { rs: ResultSet, _: Int ->
        SharedCost(
            rs.getBigDecimal("amount"),
            rs.getBoolean("isPaid"),
            rs.getString("debtor")
        )
    }
}
