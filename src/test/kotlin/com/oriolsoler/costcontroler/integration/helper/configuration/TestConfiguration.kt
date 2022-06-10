package com.oriolsoler.costcontroler.integration.helper.configuration

import com.oriolsoler.costcontroler.integration.helper.repository.CostRepositoryForTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class TestConfiguration {
    @Bean
    fun costRepositoryForTest(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
        CostRepositoryForTest(namedParameterJdbcTemplate)
}