package se.callistaenterprise.kotlindsl.repo

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import se.callistaenterprise.kotlindsl.data.DataRepositories
import se.callistaenterprise.kotlindsl.dsl.OrderDsl
import se.callistaenterprise.kotlindsl.model.Item
import se.callistaenterprise.kotlindsl.model.OrderIn
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest

@DslJooqTest
class OrderRepositoryTest(
    @Autowired val repos: DataRepositories,
    @Autowired val orderRepo: OrderRepository,
    @Autowired val customerRepo: CustomerRepository,
    @Autowired val productRepo: ProductRepository
) : DslIntegrationTest() {

    @Test
    fun `Roundtrip works`() {
        val dsl = OrderDsl()
        val customerDsl = dsl.customer("12345")
        val productDsl1 = dsl.product("A product")
        val productDsl2 = dsl.product("Another product")
        repos.persist(dsl)

        val customer = customerRepo.getById(customerDsl.id)!!
        val product1 = productRepo.getById(productDsl1.id)!!
        val product2 = productRepo.getById(productDsl2.id)!!

        val orderIn = OrderIn("1234", customer, listOf(Item(product1, 2), Item(product2, 3)))
        val id = orderRepo.create(orderIn)
        val order = orderRepo.getById(id)!!

        order.orderId shouldBe orderIn.orderId
        order.customer shouldBe orderIn.customer
        order.items shouldContainExactlyInAnyOrder orderIn.items
    }
}
