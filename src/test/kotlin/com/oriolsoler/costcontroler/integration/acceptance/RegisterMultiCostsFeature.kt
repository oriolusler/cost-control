package com.oriolsoler.costcontroler.integration.acceptance

import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.SharedCostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import com.oriolsoler.costcontroler.integration.helper.IntegrationTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals

abstract class RegisterMultiCostsFeature : IntegrationTest() {

    @Disabled
    @Test
    fun `should register new cost`() {
        mvc.post("/register/multi") {
            with(user("Oriol"))
            with(csrf())
            contentType = APPLICATION_JSON
            content = """
                [
                  {
                    "id" : "96bd1840-bc6b-4115-ab6f-db05356dc894", 
                    "date": "2022-09-12",
                    "description": "BIZUM ENVIADO",
                    "comment": "Comment 1",
                    "amount": "-0.92",
                    "category": "SHOPPING",
                    "subcategory": "SPORTING_GOODS",
                    "shared": [
                      {
                        "debtor": "Eliot",
                        "amount": "1",
                        "paid": true
                      },
                      {
                        "debtor": "Kate",
                        "amount": "5",
                        "paid": false
                      }
                    ]
                  },
                  {
                    "id" : "54d74002-1699-4c6d-adeb-5c5f95cebbfd",
                    "date": "2022-09-11",
                    "description": "BIZUM ENVIADO",
                    "comment": "Dinner with friends",
                    "amount": "-22.84",
                    "category": "FOOD",
                    "subcategory": "RESTAURANTS",
                    "shared": [
                      {
                        "debtor": "Jenny",
                        "amount": "5",
                        "paid": false
                      },
                      {
                        "debtor": "Anthony",
                        "amount": "1",
                        "paid": true
                      }
                    ]
                  }
                ]
            """.trimIndent()
        }.andExpect { status().isFound }

        val actual = costRepository.findBy("Oriol")
        assertEquals(2, actual.size)

        val expectedDto = actual.map { it.toDto() }
        assertEquals(
            expectedDto, listOf(
                CostDto(
                    LocalDate.of(2022, 9, 12),
                    "BIZUM ENVIADO",
                    "SHOPPING",
                    "SPORTING_GOODS",
                    "Comment 1",
                    "-0.92",
                    listOf(
                        SharedCostDto(BigDecimal.valueOf(1), "Eliot", true),
                        SharedCostDto(BigDecimal.valueOf(5), "Kate", false),
                    )
                ), CostDto(
                    LocalDate.of(2022, 9, 11),
                    "BIZUM RECIBIDO",
                    "FOOD",
                    "RESTAURANTS",
                    "Dinner with friends",
                    "-22.84",
                    listOf(
                        SharedCostDto(BigDecimal.valueOf(1), "Anthony", true),
                        SharedCostDto(BigDecimal.valueOf(5), "Jenny", false),
                    )
                )
            )
        )
    }
}