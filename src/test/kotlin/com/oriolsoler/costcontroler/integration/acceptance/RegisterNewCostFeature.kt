package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import io.restassured.http.ContentType.MULTIPART
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertNotNull

abstract class RegisterNewCostFeature : IntegrationTest() {

    @Test
    fun `should register new cost`() {
        given()
            .contentType(MULTIPART)
            .param("date", "2022-02-01")
            .param("description", "Spotify subscription")
            .param("category", "Online services")
            .param("subcategory", "Music")
            .param("comment", "Subscription for 5 persons")
            .param("amount", 15.99)
            .post("/register")
            .then()
            .assertThat(status().isFound)

        assertNotNull(costRepositoryForTest.findBy(Description("Spotify subscription")))
    }
}