package com.oriolsoler.costcontroler.integration.acceptance

import com.nhaarman.mockito_kotlin.eq
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType
import kotlin.test.assertNotNull

abstract class RegisterUserFeature : IntegrationTest() {

    @Test
    fun `should get user registration page`() {
        mvc.perform(get("/user/registration"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should register new user`() {
        mvc.perform(
            post("/user/registration")
                .with(csrf())
                .contentType(MULTIPART_FORM_DATA)
                .param("username", "Oriol")
                .param("password", "Oriol12345")
        ).andExpect(status().isFound)

        assertNotNull(userRepositoryForTest.findBy("oriol"))
    }

    @Test
    fun `should throw error if username already exists`() {
        userRepositoryForTest.create("Oriol")

        mvc.perform(
            post("/user/registration")
                .with(csrf())
                .contentType(MULTIPART_FORM_DATA)
                .param("username", "Oriol")
                .param("password", "Oriol12345")
        )
            .andExpect(status().isConflict)
            .andExpect(model().attribute("error", "Username oriol already exists, try another one"))
    }
}