package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


abstract class ImportExcelFeature : IntegrationTest() {
    @Test
    fun `should import excel`() {
        mvc.perform(get("/import"))
            .andExpect(status().is3xxRedirection)
    }
}