package com.oriolsoler.costcontroler.application.getcost

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException


class GetCost(private val costRepository: CostRepository) {
    fun execute(command: GetCostCommand): Cost {
        val costIdentifier = CostIdentifier(command.identifier)

        with(costRepository.findBy(costIdentifier)) {
            if (this == null) {
                throw CostNotFoundException(costIdentifier)
            }
            return this
        }
    }
}