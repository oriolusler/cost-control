package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.Id
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import java.math.BigDecimal
import java.time.LocalDate


abstract class GetCostFeature : IntegrationTest() {
    @Test
    fun `should get cost`() {
        val cost = registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            1
        )

        mvc.perform(get("/get/1").with(user("Oriol")))
            .andExpect(model().attribute("cost", equalTo(cost.toDto())))
    }

    @Test
    fun `should unauthorized cost show to unknown user`() {
        registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            1
        )

        mvc.perform(get("/get/0"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}