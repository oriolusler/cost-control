package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType
import kotlin.test.assertNotNull

abstract class RegisterUserFeature : IntegrationTest() {

    @Test
    fun `should register new user`() {
        mvc.post("/user/registration") {
            with(csrf())
            contentType(MULTIPART_FORM_DATA)
            param("username", "Oriol")
            param("password", "Oriol12345")
        }.andExpect { status().isFound }

        assertNotNull(userRepositoryForTest.findBy("Oriol"))
    }
}