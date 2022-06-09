package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

abstract class RegisterNewCostFeature : IntegrationTest() {

    @Value("classpath:register_new_cost_feature/new_cost.json")
    private lateinit var newCostJson: Resource

    @Test
    fun `should register new cost`() {
        given()
            .contentType(JSON)
            .body(newCostJson.file)
            .post("/api/cost/register")
            .then()
            .assertThat(status().isCreated)
    }
}