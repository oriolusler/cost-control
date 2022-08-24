package com.oriolsoler.costcontroler.domain.contracts

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Id
import com.oriolsoler.costcontroler.domain.SharedCost

interface CostRepository {
    fun register(cost: Cost)
    fun findBy(username: String): List<Cost>
    fun insertSharedCostFor(costId: Number, sharedCost: List<SharedCost>)
    fun findBy(id: Id): Cost?
}