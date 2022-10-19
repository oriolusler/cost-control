package com.oriolsoler.costcontroler.domain.contracts

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.CostIdentifier

interface CostRepository {
    fun register(cost: Cost)
    fun findBy(username: String): List<Cost>
    fun insertSharedCostFor(cost: Cost)
    fun findBy(costIdentifier: CostIdentifier): Cost?
    fun update(cost: Cost)
    fun updateSharedCostWith(cost: Cost)
    fun deleteSharedCostFor(costIdentifier: CostIdentifier)
    fun insertMultiRegister(costs: List<Cost>)
    fun insertMultiSharedCostFor(costs: List<Cost>)

}