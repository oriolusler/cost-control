package com.oriolsoler.costcontroler.application.getcost

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Id
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException


class GetCost(private val costRepository: CostRepository) {
    fun execute(command: GetCostCommand): Cost {
        val id = Id(command.id)

        with(costRepository.findBy(id)) {
            if (this == null) {
                throw CostNotFoundException(id)
            }
            return this
        }
    }
}