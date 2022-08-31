package com.oriolsoler.costcontroler.domain.contracts

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.SharedCost

interface CostRepository {
    fun register(cost: Cost)
    fun findBy(username: String): List<Cost>
    fun insertSharedCostFor(id: Number, shared: List<SharedCost>)
    fun findBy(costIdentifier: CostIdentifier): Cost?
    fun update(cost: Cost)
    fun updateSharedCostWith(id: Number, shared: List<SharedCost>?)
    fun deleteSharedCostFor(id: Number)
}