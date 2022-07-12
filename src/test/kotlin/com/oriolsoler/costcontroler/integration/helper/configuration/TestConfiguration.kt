package com.oriolsoler.costcontroler.integration.helper.configuration

import com.oriolsoler.costcontroler.integration.helper.repository.CostRepositoryForTest
import com.oriolsoler.costcontroler.integration.helper.repository.UserRepositoryForTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class TestConfiguration {
    @Bean
    fun costRepositoryForTest(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
        CostRepositoryForTest(namedParameterJdbcTemplate)

    @Bean
    fun userRepositoryForTest(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
        UserRepositoryForTest(namedParameterJdbcTemplate)
}