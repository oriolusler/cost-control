package com.oriolsoler.costcontroler.integration.helper

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import com.oriolsoler.costcontroler.CostControlerApplication
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.web.servlet.MockMvc

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
    }
}