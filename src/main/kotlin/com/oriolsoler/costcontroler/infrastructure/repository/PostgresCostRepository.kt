package com.oriolsoler.costcontroler.infrastructure.repository

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.ResultSet


class PostgresCostRepository(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : CostRepository {
    override fun register(cost: Cost) {
        val sql = """
            |INSERT INTO cost (date, description, category, subcategory, comment, amount, username, identifier)
            |VALUES (:date, :description, :category, :subcategory, :comment, :amount, :username, :identifier)
        """.trimMargin()

        val paramsRegister = MapSqlParameterSource()
        paramsRegister.addValue("date", cost.date)
        paramsRegister.addValue("description", cost.description!!.value)
        paramsRegister.addValue("category", cost.category?.name)
        paramsRegister.addValue("subcategory", cost.subcategory?.name)
        paramsRegister.addValue("comment", cost.comment)
        paramsRegister.addValue("amount", cost.amount)
        paramsRegister.addValue("username", cost.username)
        paramsRegister.addValue("identifier", cost.identifier.value)

        val keyHolder = GeneratedKeyHolder()

        namedParameterJdbcTemplate.update(sql, paramsRegister, keyHolder)

        val newCostId = keyHolder.keyList[0]["id"] as Number

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

    override fun findBy(costIdentifier: CostIdentifier): Cost? {
        val sql = """
            SELECT * 
            FROM COST
            WHERE identifier = :identifier
            """.trimIndent()
        val params = MapSqlParameterSource()
        params.addValue("identifier", costIdentifier.value)
        return namedParameterJdbcTemplate.query(sql, params, mapTo()).firstOrNull()
    }

    override fun insertSharedCostFor(id: Number, shared: List<SharedCost>) {
        val sql = """
            |INSERT INTO COST_SHARE (cost, amount, isPaid, debtor)
            |VALUES (:cost, :amount, :isPaid, :debtor)
        """.trimMargin()

        shared.forEach {
            val params = MapSqlParameterSource()
            params.addValue("cost", id)
            params.addValue("amount", it.amount)
            params.addValue("isPaid", it.isPaid)
            params.addValue("debtor", it.debtor)

            namedParameterJdbcTemplate.update(sql, params)
        }
    }

    override fun update(cost: Cost) {
        val sql = """
            |UPDATE COST
            |SET date=:date, description=:description, category=:category, subcategory=:subcategory, comment=:comment, amount=:amount
            |WHERE identifier=:identifier
            |AND username=:username
        """.trimMargin()

        val paramsUpdate = MapSqlParameterSource()
        paramsUpdate.addValue("date", cost.date)
        paramsUpdate.addValue("description", cost.description!!.value)
        paramsUpdate.addValue("category", cost.category?.name)
        paramsUpdate.addValue("subcategory", cost.subcategory?.name)
        paramsUpdate.addValue("comment", cost.comment)
        paramsUpdate.addValue("amount", cost.amount)
        paramsUpdate.addValue("username", cost.username)
        paramsUpdate.addValue("identifier", cost.identifier.value)

        val keyHolder = GeneratedKeyHolder()
        namedParameterJdbcTemplate.update(sql, paramsUpdate, keyHolder)

        val dbCostId = keyHolder.keyList[0]["id"] as Number

        updateSharedCostWith(dbCostId, cost.shared)
    }

    override fun updateSharedCostWith(id: Number, shared: List<SharedCost>?) {
        deleteSharedCostFor(id)
        insertSharedCostFor(id, shared!!)
    }

    override fun deleteSharedCostFor(id: Number) {
        val sql = """
            |DELETE FROM COST_SHARE
            |WHERE cost=:cost
        """.trimMargin()

        val paramsDeleteShared = MapSqlParameterSource()
        paramsDeleteShared.addValue("cost", id)
        namedParameterJdbcTemplate.update(sql, paramsDeleteShared)
    }

    private fun mapTo() = RowMapper { rs: ResultSet, _: Int ->
        Cost(
            rs.getDate("date").toLocalDate(),
            Description(rs.getString("description")),
            Categories.valueOf(rs.getString("category")),
            Subcategorises.getOrEmpty(rs.getString("subcategory")),
            rs.getString("comment"),
            rs.getBigDecimal("amount"),
            rs.getString("username"),
            getSharedCosts(rs.getInt("id")),
            CostIdentifier(rs.getString("identifier"))
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
