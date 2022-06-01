package se.callistaenterprise.kotlindsl.repo

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import se.callistaenterprise.kotlindsl.model.CustomerIn
import se.callistaenterprise.kotlindsl.model.CustomerStatus
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest

@DslJooqTest
class CustomerRepositoryTest(@Autowired val customerRepo: CustomerRepository) : DslIntegrationTest() {

    @Test
    fun `Roundtrip works`() {
        val customerIn = CustomerIn("1234", CustomerStatus.ACTIVE, "A", "Customer")
        val id = customerRepo.create(customerIn)
        val customer = customerRepo.getById(id)!!
        customer.customerId shouldBe customerIn.customerId
        customer.status shouldBe customerIn.status
        customer.firstName shouldBe customerIn.firstName
        customer.lastName shouldBe customerIn.lastName
    }

}
