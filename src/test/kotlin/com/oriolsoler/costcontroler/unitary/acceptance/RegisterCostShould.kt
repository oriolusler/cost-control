package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.toCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate.now

class RegisterCostShould {
    @Test
    fun `should register cost use case`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "Online services",
            "Television",
            "Monthly subscription",
            12.99,
            "Oriol"
        )

        val registerCostUseCase = RegisterCost(costRepository)

        registerCostUseCase.execute(registerCostCommand)

        verify(costRepository).register(registerCostCommand.toCost())
    }
}