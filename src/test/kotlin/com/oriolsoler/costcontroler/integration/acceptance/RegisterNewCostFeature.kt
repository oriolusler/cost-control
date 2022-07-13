package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType
import kotlin.test.assertNotNull

abstract class RegisterNewCostFeature : IntegrationTest() {

    @Test
    fun `should get cost registration page`(){
        mvc.get("/register").andExpect { status().isOk }
    }

    @Test
    fun `should register new cost`() {
        mvc.post("/register") {
            with(user("Oriol"))
            with(csrf())
            contentType(MULTIPART_FORM_DATA)
            param("date", "2022-02-01")
            param("description", "Spotify subscription")
            param("category", "Online services")
            param("subcategory", "Music")
            param("comment", "Subscription for 5 persons")
            param("amount", "15.99")
        }.andExpect { status().isFound }

        assertNotNull(costRepositoryForTest.findBy(Description("Spotify subscription")))
    }
}