package se.callistaenterprise.kotlindsl.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.callistaenterprise.kotlindsl.model.ProductIn;
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest;
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DslJooqTest
public class ProductRepositoryTest extends DslIntegrationTest {

    private final ProductRepository productRepo;

    public ProductRepositoryTest(@Autowired ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Test
    public void roundTripWorks() {
        var productIn = new ProductIn("1234", "A product");
        var id = productRepo.create(productIn);
        var product = productRepo.getById(id);
        assertEquals(product.productId(), productIn.productId());
        assertEquals(product.productName(), productIn.productName());
    }

}
