package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.sql.DataSource
import org.hamcrest.Matchers.`is` as hIs

abstract class ApplicationTestCase : IntegrationTest() {

    @Autowired
    private lateinit var datasource: DataSource

    @Test
    internal fun `should connect to database`() {
        val jdbcTemplate = JdbcTemplate(datasource)

        val actual = jdbcTemplate.queryForObject("SELECT version()", String::class.java)

        assertThat(actual).startsWith("PostgreSQL 13.4")
    }

    @Test
    fun `should be alive`() {
        mvc.perform(get("/actuator/health").with(user("user")))
            .andExpect(status().isOk)
            .andExpect(jsonPath("status", hIs("UP")))
    }


    @Test
    fun `should say hello world`() {
        mvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello world! This is osoler"))
    }

    @Test
    fun `should say greetings to authorized user`() {
        mvc.perform(get("/greeting").with(user("Oriol")))
            .andExpect(status().isOk)
            .andExpect(model().attribute("username", "Oriol"))
    }

    @Test
    fun `should unauthorized greetings to unknown user`() {
        mvc.perform(get("/greeting"))
            .andExpect(status().isFound)
            .andExpect(redirectedUrl("http://localhost/login"))
    }
}