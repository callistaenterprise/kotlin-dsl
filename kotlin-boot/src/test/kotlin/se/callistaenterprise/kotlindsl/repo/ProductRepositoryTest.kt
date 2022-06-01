package se.callistaenterprise.kotlindsl.repo

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import se.callistaenterprise.kotlindsl.model.ProductIn
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest

@DslJooqTest
class ProductRepositoryTest(@Autowired val productRepo: ProductRepository) : DslIntegrationTest() {

    @Test
    fun `Roundtrip works`() {
        val productIn = ProductIn("1234", "A product")
        val id = productRepo.create(productIn)
        val product = productRepo.getById(id)!!
        product.productId shouldBe productIn.productId
        product.productName shouldBe productIn.productName
    }

}
