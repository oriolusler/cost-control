package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import com.oriolsoler.costcontroler.application.getcost.GetCostCommand
import com.oriolsoler.costcontroler.application.getcost.GetCost
import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.Id
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal.ONE
import java.time.LocalDate.now
import kotlin.test.assertEquals

class GetCostShould {
    @Test
    fun `should get cost`() {
        val costRepository = mock<CostRepository>()

        val expect = Cost(
            now(),
            Description("Description"),
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment",
            ONE,
            "Oriol",
            emptyList()
        )

        val id = Id(0)

        given { costRepository.findBy(id) } willReturn { expect }

        val getCost = GetCost(costRepository)
        val command = GetCostCommand(0)
        val result = getCost.execute(command)

        verify(costRepository).findBy(id)
        assertEquals(expect, result)
    }

    @Test
    fun `throw error if cost not found`() {
        val costRepository = mock<CostRepository>()

        given { costRepository.findBy(any<Id>()) } willReturn { null }

        val getCost = GetCost(costRepository)
        val command = GetCostCommand(0)

        assertThrows<CostNotFoundException> { getCost.execute(command) }

        verify(costRepository).findBy(any<Id>())
    }
}