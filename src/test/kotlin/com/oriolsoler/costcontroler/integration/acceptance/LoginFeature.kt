package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

abstract class LoginFeature : IntegrationTest() {
    @Test
    fun `should login`() {
        mvc.perform(get("/login"))
            .andExpect(status().isOk)
    }
}