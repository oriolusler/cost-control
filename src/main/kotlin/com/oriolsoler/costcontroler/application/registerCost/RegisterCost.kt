package com.oriolsoler.costcontroler.application.registerCost

import com.oriolsoler.costcontroler.domain.contracts.CostRepository

class RegisterCost(private val costRepository: CostRepository) {
    fun execute(registerCostCommand: RegisterCostCommand) {
        costRepository.register(registerCostCommand.toCost())
    }
}