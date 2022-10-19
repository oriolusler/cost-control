package com.oriolsoler.costcontroler.infrastructure.configuration

import com.oriolsoler.costcontroler.application.getcost.GetCost
import com.oriolsoler.costcontroler.application.registerCost.multi.MultiRegisterCost
import com.oriolsoler.costcontroler.application.registerCost.single.RegisterCost
import com.oriolsoler.costcontroler.application.registerUser.RegisterUser
import com.oriolsoler.costcontroler.application.showCosts.ShowCosts
import com.oriolsoler.costcontroler.application.updateCost.UpdateCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfiguration {

    @Bean
    fun registerUser(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ) = RegisterUser(userRepository, passwordEncoder)

    @Bean
    fun registerCost(costRepository: CostRepository) = RegisterCost(costRepository)

    @Bean
    fun showCost(costRepository: CostRepository) = ShowCosts(costRepository)

    @Bean
    fun getCost(costRepository: CostRepository) = GetCost(costRepository)

    @Bean
    fun updateCost(costRepository: CostRepository) = UpdateCost(costRepository)

    @Bean
    fun multiRegisterCost(costRepository: CostRepository) = MultiRegisterCost(costRepository)
}