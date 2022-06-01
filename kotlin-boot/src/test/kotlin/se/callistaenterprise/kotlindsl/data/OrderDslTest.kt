package se.callistaenterprise.kotlindsl.data

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest
import se.callistaenterprise.kotlindsl.testutil.DslSpringBootTest

@DslSpringBootTest
class OrderDslTest(@Autowired val repos: DataRepositories) : DslIntegrationTest() {

    @Test
    fun `Test inline`() {
        repos.persist {
            val ann = customer {
                firstName = "Ann"
                lastName = "Svensson"
            }

            val cal = customer {
                firstName = "Cal"
                lastName = "Carlsson"
            }

            val computer = product("Computer")
            val keyboard = product("Keyboard")
            val mouse = product("Mouse")

            order(ann) {
                item(computer, 2)
                item(keyboard)
                item(mouse)
            }

            order(cal) {
                item(computer)
                item(keyboard)
                item(mouse, 2)
            }
        }
    }

    @Test
    fun `Test demodata file`() {
        val dsl = DslUtil.evalScript(fileContents("/dsl/demodata.orderdsl.kts"), "demodata.orderdsl.kts")

        repos.persist(dsl)
    }

    private fun fileContents(file: String) = this::class.java.getResource(file)!!.readText()

}
