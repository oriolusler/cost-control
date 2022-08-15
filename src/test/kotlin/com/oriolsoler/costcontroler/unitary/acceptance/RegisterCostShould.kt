package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.toCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.InvalidPendingAmountException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
            "Oriol",
            false,
            null
        )

        val registerCostUseCase = RegisterCost(costRepository)

        registerCostUseCase.execute(registerCostCommand)

        verify(costRepository).register(registerCostCommand.toCost())
    }

    @Test
    fun `should register cost use case with pending amount to pay`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "Online services",
            "Television",
            "Monthly subscription",
            12.99,
            "Oriol",
            true,
            10.32
        )

        val registerCostUseCase = RegisterCost(costRepository)

        registerCostUseCase.execute(registerCostCommand)

        verify(costRepository).register(registerCostCommand.toCost())
    }

    @Test
    fun `should throw an exception if cost is pending to pay but not amount set`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "Online services",
            "Television",
            "Monthly subscription",
            12.99,
            "Oriol",
            true,
            null
        )

        val registerCostUseCase = RegisterCost(costRepository)

        assertThrows<InvalidPendingAmountException> { registerCostUseCase.execute(registerCostCommand) }

        verify(costRepository, never()).register(any())
    }
}