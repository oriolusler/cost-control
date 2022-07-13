package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model

abstract class ShowAllCostsFeature : IntegrationTest() {
    @Test
    fun `should show all costs`() {
        val cost1 = registerCost("Description cost 1", "Category1", "Subcategory1", "Comment1", 1.0, "Oriol")
        val cost2 = registerCost("Description cost 2", "Category2", "Subcategory2", "Comment2", 2.0, "Oriol")

        mvc.perform(get("/show").with(user("Oriol")))
            .andExpect(model().attribute("costs", hasItem(cost1.toDto())))
            .andExpect(model().attribute("costs", hasItem(cost2.toDto())))
    }

    @Test
    fun `should unauthorized cost show to unknown user`() {
        mvc.perform(get("/show"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}