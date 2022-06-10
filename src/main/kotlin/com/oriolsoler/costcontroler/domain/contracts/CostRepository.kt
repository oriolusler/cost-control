package com.oriolsoler.costcontroler.domain.contracts

interface CostRepository {
    fun register(cost: Cost)
}