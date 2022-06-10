package com.oriolsoler.costcontroler.infrastructure.configuration

import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.infrastructure.repository.PostgresCostRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class InfrastructureConfiguration {

    @Bean
    fun costRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate): CostRepository =
        PostgresCostRepository(namedParameterJdbcTemplate)
}