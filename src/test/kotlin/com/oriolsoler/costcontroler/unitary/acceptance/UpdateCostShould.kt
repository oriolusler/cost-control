package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import com.oriolsoler.costcontroler.application.updateCost.UpdateCost
import com.oriolsoler.costcontroler.application.updateCost.UpdateCostCommand
import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDate.now

class UpdateCostShould {

    @Test
    fun `should update cost use case`() {
        val costRepository = mock<CostRepository>()

        val costIdentifier = CostIdentifier()
        val dateNow = now()

        given { costRepository.findBy(costIdentifier) } willReturn {
            Cost(
                dateNow,
                Description("Description"),
                Categories.FOOD,
                Subcategorises.GROCERIES,
                "comment",
                BigDecimal.TEN,
                "Jonny",
                emptyList(),
                costIdentifier,
                "N26"
            )
        }


        val updateCostCommand = UpdateCostCommand(
            dateNow,
            "Netflix subscription",
            "RECREATION_ENTERTAINMENT",
            "STREAMING_SERVICES",
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            emptyList(),
            costIdentifier.value.toString(),
            "BBVA"
        )

        val updateCostUseCase = UpdateCost(costRepository)

        updateCostUseCase.execute(updateCostCommand)

        verify(costRepository).findBy(costIdentifier)

        val updatedCost = Cost(
            dateNow,
            Description("Netflix subscription"),
            Categories.RECREATION_ENTERTAINMENT,
            Subcategorises.STREAMING_SERVICES,
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            emptyList(),
            costIdentifier,
            "BBVA"
        )
        verify(costRepository).update(updatedCost)
    }

    @Test
    fun `should throw exception if cost to update is not updated`() {
        val costRepository = mock<CostRepository>()
        val updateCostUseCase = UpdateCost(costRepository)
        val costIdentifier = CostIdentifier()

        given { costRepository.findBy(costIdentifier) } willReturn { null }
        
        val updateCostCommand = UpdateCostCommand(
            now(),
            "Netflix subscription",
            "RECREATION_ENTERTAINMENT",
            "STREAMING_SERVICES",
            "Monthly subscription",
            BigDecimal.valueOf(12.99),
            "Oriol",
            emptyList(),
            costIdentifier.value.toString()
        )

        assertThrows<CostNotFoundException> { updateCostUseCase.execute(updateCostCommand) }
    }
}