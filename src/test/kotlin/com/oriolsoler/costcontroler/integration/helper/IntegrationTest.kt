package com.oriolsoler.costcontroler.integration.helper

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import com.oriolsoler.costcontroler.CostControlerApplication
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.integration.helper.repository.CostRepositoryForTest
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDate

@SpringBootTest(
    classes = [CostControlerApplication::class],
    properties = ["spring.profiles.active=integration-test"]
)
@AutoConfigureMockMvc
class IntegrationTest {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var env: Environment

    @Autowired
    lateinit var costRepository: CostRepository

    @Autowired
    lateinit var costRepositoryForTest: CostRepositoryForTest

    companion object {
        val wireMockServer: WireMockServer = WireMockServer(
            options()
                .port(8888)
                .notifier(ConsoleNotifier(true))
        )

        @BeforeAll
        @JvmStatic
        fun setUpClass() {
            wireMockServer.start()
        }

    }

    @BeforeEach
    fun setUp() {
        mockMvc(mvc)
        wireMockServer.resetAll()
        costRepositoryForTest.truncate()
    }

    fun registerCost(
        description: String,
        category: String,
        subcategory: String,
        comment: String,
        amount: Double
    ): Cost {
        val desc = Description(description)
        val cost = Cost(LocalDate.now(), desc, category, subcategory, comment, amount)
        costRepository.register(cost)
        return cost
    }
}
