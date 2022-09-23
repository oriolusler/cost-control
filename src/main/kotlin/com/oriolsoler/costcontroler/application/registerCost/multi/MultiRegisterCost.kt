package com.oriolsoler.costcontroler.application.registerCost.multi

import com.oriolsoler.costcontroler.domain.contracts.CostRepository

class MultiRegisterCost(private val costRepository: CostRepository) {
    fun execute(multiRegisterCostCommand: MultiRegisterCostCommand) {
        val costs = multiRegisterCostCommand.toCosts()
        costRepository.multiRegister(costs)
    }
}