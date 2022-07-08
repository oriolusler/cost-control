package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model

abstract class ShowAllCostsFeature : IntegrationTest() {
    @Test
    fun `should show all costs`(){
        val cost1 = registerCost("Description cost 1", "Category1", "Subcategory1", "Comment1", 1.0)
        val cost2 = registerCost("Description cost 2", "Category2", "Subcategory2", "Comment2", 2.0)

        given()
            .get("/show")
            .then()
            .expect(model().attributeExists("costs"))
            .expect(model().attribute("costs", hasItem(cost1.toDto())))
            .expect(model().attribute("costs", hasItem(cost2.toDto())))
    }
}