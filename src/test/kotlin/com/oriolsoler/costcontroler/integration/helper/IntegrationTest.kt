package com.oriolsoler.costcontroler.integration.helper

import com.oriolsoler.costcontroler.CostControlerApplication
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.contracts.CostRepository
import com.oriolsoler.costcontroler.integration.helper.repository.CostRepositoryForTest
import com.oriolsoler.costcontroler.integration.helper.repository.UserRepositoryForTest
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.math.BigDecimal
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

    @Autowired
    lateinit var userRepositoryForTest: UserRepositoryForTest

    @Autowired
    lateinit var context: WebApplicationContext

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
        .build();
        costRepositoryForTest.truncate()
    }

    fun registerCost(
        description: String,
        category: String,
        subcategory: String,
        comment: String,
        amount: BigDecimal,
        user: String,
        shared: List<SharedCost>
    ): Cost {
        val desc = Description(description)
        val cost = Cost(
            LocalDate.now(),
            desc,
            category,
            subcategory,
            comment,
            amount,
            user,
            shared
        )
        costRepository.register(cost)
        return cost
    }
}
