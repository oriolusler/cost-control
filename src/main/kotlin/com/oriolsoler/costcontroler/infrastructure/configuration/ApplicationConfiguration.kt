package com.oriolsoler.costcontroler.infrastructure.configuration

import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun registerCost(costRepository: CostRepository) = RegisterCost(costRepository)
}