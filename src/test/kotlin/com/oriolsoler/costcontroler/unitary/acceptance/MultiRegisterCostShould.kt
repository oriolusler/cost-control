package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.oriolsoler.costcontroler.application.registerCost.multi.MultiRegisterCost
import com.oriolsoler.costcontroler.application.registerCost.multi.MultiRegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.single.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.single.SharedCostCommand
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate.now
import kotlin.test.assertEquals

class MultiRegisterCostShould {

    @Test
    fun `should register multi costs`() {
        val costRepository = mock<CostRepository>()
        val registerCostCommand1 = RegisterCostCommand(
            now(),
            "Netflix subscription",
            "RECREATION_ENTERTAINMENT",
            "STREAMING_SERVICES",
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            listOf(SharedCostCommand(BigDecimal.TEN, "Jonny", false))
        )
        val registerCostCommand2 = RegisterCostCommand(
            now(),
            "Lasal",
            "FOOD",
            "RESTAURANTS",
            "Birthday celebration",
            BigDecimal.valueOf(39.99),
            "Oriol",
            listOf(SharedCostCommand(BigDecimal.TEN, "Jonny", false))
        )

        val multiRegisterCostCommand = MultiRegisterCostCommand(listOf(registerCostCommand1, registerCostCommand2))

        val multiRegisterCostUseCase = MultiRegisterCost(costRepository)

        multiRegisterCostUseCase.execute(multiRegisterCostCommand)

        val captor = argumentCaptor<List<Cost>>()
        verify(costRepository).multiRegister(captor.capture())

        val capturedCost = captor.firstValue
        assertEquals(2, capturedCost.size)
    }
}