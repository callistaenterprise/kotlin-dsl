package se.callistaenterprise.kotlindsl.testutil

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer

const val DBNAME = "orderdsl"
const val DBUSERNAME = "test"
const val DBPASSWORD = "test"

const val CONTAINER_VERSION = "postgres:14"

@ContextConfiguration(initializers = [DslIntegrationTest.Initializer::class])
abstract class DslIntegrationTest {

    companion object {
        val container = PostgreSQLContainer<Nothing>(CONTAINER_VERSION)
            .apply {
                withDatabaseName(DBNAME)
                withUsername(DBUSERNAME)
                withPassword(DBPASSWORD)
            }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            container.start()
            println("Starting testcontainer on port ${container.firstMappedPort}")
            TestPropertyValues.of(
                "spring.datasource.url=jdbc:postgresql://${container.host}:${container.firstMappedPort}/$DBNAME",
                "spring.datasource.username=$DBUSERNAME",
                "spring.datasource.password=$DBPASSWORD",
            ).applyTo(configurableApplicationContext.environment)
        }
    }

}
