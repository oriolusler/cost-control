package com.oriolsoler.costcontroler.integration.acceptance


import com.nhaarman.mockito_kotlin.eq
import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.math.BigDecimal
import kotlin.test.assertEquals

abstract class UpdateCostFeature : IntegrationTest() {

    @Test
    fun `should update cost`() {
        val cost = registerCost(
            "Description cost 1",
            Categories.FOOD,
            Subcategorises.GROCERIES,
            "Comment1",
            BigDecimal.valueOf(1.0),
            "Oriol",
            emptyList(),
            CostIdentifier()
        )
        val actual = costRepository.findBy(cost.identifier)
        assertEquals("Description cost 1", actual?.description?.value)
        assertEquals(Categories.FOOD, actual?.category)
        assertEquals(Subcategorises.GROCERIES, actual?.subcategory)
        assertEquals("Comment1", actual?.comment)
        assertEquals(BigDecimal.valueOf(1.0), actual?.amount)
        assertEquals(0, actual?.shared?.size)

        mvc.post("/update") {
            with(user("Oriol"))
            with(csrf())
            contentType(MULTIPART_FORM_DATA)
            params
            param("id", "${cost.identifier.value}")
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

        val updated = costRepository.findBy(cost.identifier)
        assertEquals("Spotify subscription", updated?.description?.value)
        assertEquals(Categories.RECREATION_ENTERTAINMENT, updated?.category)
        assertEquals(Subcategorises.STREAMING_SERVICES, updated?.subcategory)
        assertEquals("Subscription for 5 persons", updated?.comment)
        assertEquals(BigDecimal.valueOf(15.99), updated?.amount)
        assertEquals(2, updated?.shared?.size)

    }
/*

    @Test
    fun `should throw error if cost doesn't exists while updating`() {
        mvc.post("/update") {
            with(user("Oriol"))
            with(csrf())
            contentType(MULTIPART_FORM_DATA)
            params
            param("id", "61049049-13f7-4c87-99a6-99852af28eda")
            param("date", "2022-01-01")
            param("description", "Spotify subscription")
            param("category", "RECREATION_ENTERTAINMENT")
            param("subcategory", "STREAMING_SERVICES")
            param("comment", "Subscription for 5 persons")
            param("amount", "15.99")
            param("shared[0].amount", "2.67")
            param("shared[0].debtor", "Jonny")
            param("shared[0].paid", "true")
        }.andExpect {
            status { isBadRequest() }
            content { eq("Cost with id: 61049049-13f7-4c87-99a6-99852af28ed not found") }
        }
    }
*/

    @Test
    fun `should unauthorized cost edition to unknown user`() {
        mvc.perform(MockMvcRequestBuilders.get("/register"))
            .andExpect(status().isFound)
            .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }
}