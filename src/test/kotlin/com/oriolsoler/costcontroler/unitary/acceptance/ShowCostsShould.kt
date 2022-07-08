package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import com.oriolsoler.costcontroler.application.showCosts.ShowCosts
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate.now
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShowCostsShould {
    @Test
    fun `should show costs`() {
        val costRepository = mock<CostRepository>()
        given { costRepository.find() } willReturn {
            listOf(
                Cost(
                    now(),
                    Description("Description"),
                    "Category",
                    "Subcategory",
                    "Comment",
                    1.0
                )
            )
        }

        val showCostsUseCase = ShowCosts(costRepository)

        val result = showCostsUseCase.execute()
        assertEquals(1, result.size)

        verify(costRepository).find()
    }
}