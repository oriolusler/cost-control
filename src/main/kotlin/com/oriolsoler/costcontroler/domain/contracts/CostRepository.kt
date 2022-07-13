package com.oriolsoler.costcontroler.domain.contracts

import com.oriolsoler.costcontroler.domain.Cost

interface CostRepository {
    fun register(cost: Cost)
    fun findBy(username: String): List<Cost>
}