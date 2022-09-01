package com.oriolsoler.costcontroler.application.updateCost

import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException

class UpdateCost(private val costRepository: CostRepository) {
    fun execute(updateCostCommand: UpdateCostCommand) {
        val cost = updateCostCommand.toCost()
        if (costRepository.findBy(cost.identifier) == null) {
            throw CostNotFoundException(cost.identifier)
        }
        costRepository.update(cost)
    }
}