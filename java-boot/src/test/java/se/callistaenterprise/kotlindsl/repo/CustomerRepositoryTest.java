package se.callistaenterprise.kotlindsl.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.callistaenterprise.kotlindsl.model.CustomerIn;
import se.callistaenterprise.kotlindsl.model.CustomerStatus;
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest;
import se.callistaenterprise.kotlindsl.testutil.DslJooqTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DslJooqTest
public class CustomerRepositoryTest extends DslIntegrationTest {

    private final CustomerRepository customerRepo;

    CustomerRepositoryTest(@Autowired CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Test
    public void roundTripWorks() {
        var customerIn = new CustomerIn("1234", CustomerStatus.ACTIVE, "A", "Customer");
        var id = customerRepo.create(customerIn);
        var customer = customerRepo.getById(id);
        assertEquals(customer.customerId(), customerIn.customerId());
        assertEquals(customer.status(), customerIn.status());
        assertEquals(customer.firstName(), customerIn.firstName());
        assertEquals(customer.lastName(), customerIn.lastName());
    }

}
