package com.oriolsoler.costcontroler.application.showCosts

import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto

class ShowCosts(private val costRepository: CostRepository) {
    fun execute() = costRepository.find().map { it.toDto() }
}