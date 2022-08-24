package com.oriolsoler.costcontroler.integration

import com.oriolsoler.costcontroler.integration.acceptance.ApplicationTestCase
import com.oriolsoler.costcontroler.integration.acceptance.GetCostFeature
import com.oriolsoler.costcontroler.integration.acceptance.LoginFeature
import com.oriolsoler.costcontroler.integration.acceptance.RegisterNewCostFeature
import com.oriolsoler.costcontroler.integration.acceptance.RegisterUserFeature
import com.oriolsoler.costcontroler.integration.acceptance.ShowAllCostsFeature
import com.oriolsoler.costcontroler.integration.helper.docker.DockerComposeHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ApplicationIntegrationTest {

    companion object {

        @Container
        private val dockerComposeContainer = DockerComposeHelper.create()

        @BeforeAll
        @JvmStatic
        fun setSystemProperties() {
            DockerComposeHelper.setSystemProperties(dockerComposeContainer)
        }
    }

    @Nested
    inner class ApplicationNested : ApplicationTestCase()

    @Nested
    inner class RegisterNewCostFeatureNested : RegisterNewCostFeature()

    @Nested
    inner class ShowAllCostsFeatureNested : ShowAllCostsFeature()

    @Nested
    inner class LoginFeatureNested : LoginFeature()

    @Nested
    inner class RegisterUserFeatureNested : RegisterUserFeature()

    @Nested
    inner class GetCostFeatureNested : GetCostFeature()
}