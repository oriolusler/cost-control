package com.oriolsoler.costcontroler.integration.helper


import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait.forListeningPort
import org.testcontainers.containers.wait.strategy.Wait.forLogMessage
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import java.io.File
import java.lang.System.setProperty

class DockerComposeHelper {

    companion object {

        /* SET HERE YOUR SERVICE
        private const val SERVICE = "service"
        private const val SERVICE_PORT = 1234
        */

        fun create(): DockerComposeContainer<*> {
            return DockerComposeContainer<Nothing>(File("docker-compose.yml"))
            /*  .apply { withLocalCompose(true) }
                .apply {
                    withExposedService(
                        SERVICE,
                        SERVICE_PORT,
                        WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
                            .apply { withStrategy(forListeningPort()) }
                            .apply {
                                withStrategy(
                                    forLogMessage(
                                        ".*database system is ready to accept connections.*",
                                        1
                                    )
                                )
                            }
                    )
                }*/
        }

        fun setSystemProperties(container: DockerComposeContainer<*>) {
            /*val postgresHost = container.getServiceHost(SERVICE, SERVICE_PORT)
            val postgresPort = container.getServicePort(SERVICE, SERVICE_PORT)
            setProperty("database.host", postgresHost)
            setProperty("database.port", postgresPort.toString())*/
        }
    }
}