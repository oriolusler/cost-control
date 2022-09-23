package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.oriolsoler.costcontroler.application.registerCost.single.RegisterCost
import com.oriolsoler.costcontroler.application.registerCost.single.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.single.SharedCostCommand
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate.now
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RegisterCostShould {
    @Test
    fun `should register cost use case`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "RECREATION_ENTERTAINMENT",
            "STREAMING_SERVICES",
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            emptyList()
        )

        val registerCostUseCase = RegisterCost(costRepository)

        registerCostUseCase.execute(registerCostCommand)

        val captor = argumentCaptor<Cost>()
        verify(costRepository).register(captor.capture())

        val capturedCost = captor.firstValue

        assertEquals("Netflix subscription", capturedCost.description?.value)
        assertEquals("RECREATION_ENTERTAINMENT", capturedCost.category?.name)
        assertEquals("STREAMING_SERVICES", capturedCost.subcategory?.name)
        assertEquals("Monthly subscription", capturedCost.comment)
        assertEquals(BigDecimal.valueOf(12.99), capturedCost.amount)
        assertEquals("Oriol", capturedCost.username)
        assertNotNull( capturedCost.identifier)
    }

    @Test
    fun `should register cost use case with pending amount to pay`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "RECREATION_ENTERTAINMENT",
            "STREAMING_SERVICES",
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            listOf(SharedCostCommand(BigDecimal.TEN, "Jonny", false))
        )

        val registerCostUseCase = RegisterCost(costRepository)

        registerCostUseCase.execute(registerCostCommand)

        val captor = argumentCaptor<Cost>()
        verify(costRepository).register(captor.capture())

        val capturedCost = captor.firstValue
        assertEquals(listOf(SharedCost(BigDecimal.TEN, false, "Jonny")), capturedCost.shared)
    }
}