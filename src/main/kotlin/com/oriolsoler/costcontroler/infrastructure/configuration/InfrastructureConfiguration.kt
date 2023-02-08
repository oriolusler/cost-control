package com.oriolsoler.costcontroler.infrastructure.configuration

import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import com.oriolsoler.costcontroler.infrastructure.repository.PostgresCostRepository
import com.oriolsoler.costcontroler.infrastructure.repository.PostgresUserRepository
import com.oriolsoler.costcontroler.infrastructure.repository.view.PostgresGetCostsRepository
import com.oriolsoler.costcontroler.infrastructure.repository.view.PostgresPendingSharedCostViewRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class InfrastructureConfiguration {

    @Bean
    fun costRepository(
        namedParameterJdbcTemplate: NamedParameterJdbcTemplate
    ): CostRepository = PostgresCostRepository(namedParameterJdbcTemplate)

    @Bean
    fun userRepository(
        namedParameterJdbcTemplate: NamedParameterJdbcTemplate
    ): UserRepository = PostgresUserRepository(namedParameterJdbcTemplate)

    @Bean
    fun postgresPendingSharedCostViewRepository(
        namedParameterJdbcTemplate: NamedParameterJdbcTemplate
    ) = PostgresPendingSharedCostViewRepository(namedParameterJdbcTemplate)

    @Bean
    fun postgresGetCostsRepository(
        namedParameterJdbcTemplate: NamedParameterJdbcTemplate
    ) = PostgresGetCostsRepository(namedParameterJdbcTemplate)
}