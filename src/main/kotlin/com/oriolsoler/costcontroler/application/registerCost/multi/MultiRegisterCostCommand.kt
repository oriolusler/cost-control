package com.oriolsoler.costcontroler.application.registerCost.multi

import com.oriolsoler.costcontroler.application.registerCost.single.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.single.toCost
import com.oriolsoler.costcontroler.domain.Cost

data class MultiRegisterCostCommand(val costs: List<RegisterCostCommand>) {
    fun toCosts(): List<Cost> {
        return costs.map { it.toCost() }
    }
}