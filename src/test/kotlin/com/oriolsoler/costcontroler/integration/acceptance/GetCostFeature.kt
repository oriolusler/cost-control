package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import java.math.BigDecimal


abstract class GetCostFeature : IntegrationTest() {
    @Test
    fun `should get cost`() {
        val costIdentifier = CostIdentifier()

        val cost = registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            costIdentifier
        )

        mvc.perform(get("/get/${costIdentifier.value.toString()}").with(user("Oriol")))
            .andExpect(model().attribute("cost", equalTo(cost.toDto())))
    }

    @Test
    fun `should unauthorized cost show to unknown user`() {
        val costIdentifier = CostIdentifier()
        registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            costIdentifier
        )

        mvc.perform(get("/get/${costIdentifier.value.toString()}"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}