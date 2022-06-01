package se.callistaenterprise.kotlindsl.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.callistaenterprise.kotlindsl.data.DataRepositories;
import se.callistaenterprise.kotlindsl.dsl.OrderDsl;
import se.callistaenterprise.kotlindsl.model.Item;
import se.callistaenterprise.kotlindsl.model.OrderIn;
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest;
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DslJooqTest
public class OrderRepositoryTest extends DslIntegrationTest {

    private final DataRepositories repos;
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public OrderRepositoryTest(
            @Autowired DataRepositories repos,
            @Autowired OrderRepository orderRepo,
            @Autowired CustomerRepository customerRepo,
            @Autowired ProductRepository productRepo) {
        this.repos = repos;
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Test
    public void roundTripWorks() {
        var dsl = new OrderDsl();
        var customerDsl = dsl.customer("12345");
        var productDsl1 = dsl.product("A product");
        var productDsl2 = dsl.product("Another product");
        repos.persist(dsl);

        var customer = customerRepo.getById(customerDsl.getId());
        var product1 = productRepo.getById(productDsl1.getId());
        var product2 = productRepo.getById(productDsl2.getId());

        var orderIn = new OrderIn("1234", customer, Arrays.asList(new Item(product1, 2L), new Item(product2, 3L)));
        var id = orderRepo.create(orderIn);
        var order = orderRepo.getById(id);

        assertEquals(order.orderId(), orderIn.orderId());
        assertEquals(order.customer(), orderIn.customer());
        assertTrue(order.items().containsAll(orderIn.items()));
        assertTrue(orderIn.items().containsAll(order.items()));
    }
}
