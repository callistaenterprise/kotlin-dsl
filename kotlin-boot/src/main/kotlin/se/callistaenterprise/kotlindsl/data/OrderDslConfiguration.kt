package se.callistaenterprise.kotlindsl.data

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import se.callistaenterprise.kotlindsl.data.DslUtil.Companion.evalScript
import javax.annotation.PostConstruct

/*
  This configuration ensures that src/main/resources/dsl/demodata.orderdsl.kts is automatically interpreted by the dsl functionality at startup.
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Configuration
@Profile("!test")
class OrderDslConfiguration(val repos: DataRepositories) {

    @PostConstruct
    fun setupTestData() {
        val dataFile = "demodata.orderdsl.kts"
        val dsl = evalScript(fileContents("/dsl/$dataFile"), dataFile)
        repos.persist(dsl, dataFile)
    }

    private fun fileContents(file: String) = this::class.java.getResource(file)!!.readText()
}
