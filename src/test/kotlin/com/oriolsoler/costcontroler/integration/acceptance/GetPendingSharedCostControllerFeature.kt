package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.infrastructure.repository.view.PendingSharedCostViewDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import java.math.BigDecimal.*


abstract class GetPendingSharedCostControllerFeature : IntegrationTest() {
    @Test
    fun `should get pending cost pending to pay`() {
        val costIdentifier1 = CostIdentifier()
        val costIdentifier2 = CostIdentifier()

        val cost1 = registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            valueOf(15.0),
            "Oriol",
            listOf(
                SharedCost(TEN, false, "Jimmy"),
                SharedCost(ONE, true, "Pablo")
            ),
            costIdentifier1
        )

        val cost2 = registerCost(
            "Description cost 2",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            valueOf(20.0),
            "Oriol",
            listOf(
                SharedCost(valueOf(5), false, "Kessie"),
                SharedCost(valueOf(10), true, "Jordi")
            ),
            costIdentifier2
        )

        mvc.perform(
            get("/get-pending-to-pay").with(user("Oriol"))
        ).andExpect(model().attribute("costs", hasSize<Int>(2)))
            .andExpect(
                model().attribute(
                    "costs", hasItem(
                        PendingSharedCostViewDto(
                            "Description cost 1",
                            costIdentifier1.value.toString(),
                            valueOf(10.00),
                            "Jimmy",
                            cost1.date!!
                        )
                    )
                )
            )
            .andExpect(
                model().attribute(
                    "costs", hasItem(
                        PendingSharedCostViewDto(
                            "Description cost 2",
                            costIdentifier2.value.toString(),
                            valueOf(5.00),
                            "Kessie",
                            cost2.date!!
                        )
                    )
                )
            )
    }

}