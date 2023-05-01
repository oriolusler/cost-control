package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import java.math.BigDecimal


abstract class ShowAllCostsFeature : IntegrationTest() {
    @Test
    fun `should show all user costs`() {
        val cost1 = registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            CostIdentifier(),
            "N26"
        )
        val cost2 = registerCost(
            "Description cost 2",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment2",
            BigDecimal.valueOf(2.0),
            "Oriol",
            emptyList(),
            CostIdentifier(),
            "N26"
        )
        val cost3 = registerCost(
            "Description cost 3",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment3",
            BigDecimal.valueOf(3.0),
            "Jonny",
            emptyList(),
            CostIdentifier(),
            "N26"
        )

        mvc.perform(get("/show").with(user("Oriol")))
            .andExpect(model().attribute("costs", hasSize<Int>(2)))
            .andExpect(model().attribute("costs", hasItem(cost1.toDto())))
            .andExpect(model().attribute("costs", hasItem(cost2.toDto())))

        mvc.perform(get("/show").with(user("Jonny")))
            .andExpect(model().attribute("costs", hasSize<Int>(1)))
            .andExpect(model().attribute("costs", hasItem(cost3.toDto())))
    }

    @Test
    fun `should unauthorized cost show to unknown user`() {
        mvc.perform(get("/show"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}