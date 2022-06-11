package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.sql.DataSource

abstract class ApplicationTestCase : IntegrationTest() {

    @Autowired
    private lateinit var datasource: DataSource

    @Test
    internal fun `should connect to database`() {
        val jdbcTemplate = JdbcTemplate(datasource)

        val actual = jdbcTemplate.queryForObject("SELECT version()", String::class.java)

        Assertions.assertThat(actual).startsWith("PostgreSQL 14.2")
    }

    @Test
    fun `should be alive`() {
        given()
            .`when`()
            .get("/actuator/health")
            .then()
            .assertThat(status().isOk)
            .body("status", equalTo("UP"))
    }



    @Test
    fun `should say hello world`() {
        given()
            .`when`()
            .get("/")
            .then()
            .assertThat(status().isOk)
            .body(equalTo("Hello world! This is osoler"))
    }
}