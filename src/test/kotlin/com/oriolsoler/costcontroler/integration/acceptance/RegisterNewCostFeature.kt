package com.oriolsoler.costcontroler.integration.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.infrastructure.controller.dto.SharedCostDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

abstract class RegisterNewCostFeature : IntegrationTest() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
            param("category", "RECREATION_ENTERTAINMENT")
            param("subcategory", "STREAMING_SERVICES")
            param("comment", "Subscription for 5 persons")
            param("amount", "15.99")
        }.andExpect { status().isFound }

        val actual = costRepository.findBy("Oriol")
        assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `should register new cost with shared costs`() {
        mvc.post("/register") {
            with(user("Oriol"))
            with(csrf())
            contentType(MULTIPART_FORM_DATA)
            params
            param("date", "2022-01-01")
            param("description", "Spotify subscription")
            param("category", "RECREATION_ENTERTAINMENT")
            param("subcategory", "STREAMING_SERVICES")
            param("comment", "Subscription for 5 persons")
            param("amount", "15.99")
            param("shared[0].amount", "2.67")
            param("shared[0].debtor", "Jonny")
            param("shared[0].paid", "true")
            param("shared[1].amount", "2.67")
            param("shared[1].debtor", "Merry")
            param("shared[1].paid", "false")
        }.andExpect { status().isFound }

        val actual = costRepository.findBy("Oriol")
        assertTrue(actual[0].shared!!.isNotEmpty())

    }

    @Test
    fun `should unauthorized cost registration to unknown user`() {
        mvc.perform(MockMvcRequestBuilders.get("/register"))
            .andExpect(status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}