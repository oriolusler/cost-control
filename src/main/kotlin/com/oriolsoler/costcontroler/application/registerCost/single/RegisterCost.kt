package com.oriolsoler.costcontroler.application.registerCost.single

import com.oriolsoler.costcontroler.domain.contracts.CostRepository

class RegisterCost(private val costRepository: CostRepository) {
    fun execute(registerCostCommand: RegisterCostCommand) {
        val cost = registerCostCommand.toCost()
        costRepository.register(cost)
    }
}