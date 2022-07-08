package com.oriolsoler.costcontroler.application.showCosts

import com.oriolsoler.costcontroler.domain.contracts.CostRepository

class ShowCosts(private val costRepository: CostRepository) {
    fun execute() = costRepository.find()
}