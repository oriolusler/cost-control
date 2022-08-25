package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.SharedCostCommand
import com.oriolsoler.costcontroler.application.registerCost.toCost
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.InvalidPendingAmountException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Captor
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
        assertNotNull( capturedCost.costIdentifier)
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