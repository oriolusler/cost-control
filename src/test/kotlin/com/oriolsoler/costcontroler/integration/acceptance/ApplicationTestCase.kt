package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

abstract class ApplicationTestCase : IntegrationTest() {
    @Test
    fun `should be alive`() {
        given()
            .`when`()
            .get("/actuator/health")
            .then()
            .assertThat(status().isOk)
            .body("status", equalTo("UP"))
    }
}