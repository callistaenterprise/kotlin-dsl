package se.callistaenterprise.kotlindsl.data;

import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.callistaenterprise.kotlindsl.dsl.Active;
import se.callistaenterprise.kotlindsl.dsl.CustomerDsl;
import se.callistaenterprise.kotlindsl.dsl.CustomerOrderDsl;
import se.callistaenterprise.kotlindsl.dsl.CustomerStatus;
import se.callistaenterprise.kotlindsl.dsl.OrderDsl;
import se.callistaenterprise.kotlindsl.dsl.ProductDsl;
import se.callistaenterprise.kotlindsl.model.Customer;
import se.callistaenterprise.kotlindsl.model.CustomerIn;
import se.callistaenterprise.kotlindsl.model.Item;
import se.callistaenterprise.kotlindsl.model.OrderIn;
import se.callistaenterprise.kotlindsl.model.Product;
import se.callistaenterprise.kotlindsl.model.ProductIn;
import se.callistaenterprise.kotlindsl.repo.CustomerRepository;
import se.callistaenterprise.kotlindsl.repo.OrderRepository;
import se.callistaenterprise.kotlindsl.repo.ProductRepository;

import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/*
   Component responsible for persisting an instance of OrderDsl to the database. The OrdeDsl instance is traversed from root
   to bottom, and each entity found is persisted using the business logic repositories.

   The name of the script that has been used to create the OrderDsl is also saved in the table "order_dsl", so that the same
   script will not be persisted twice (e.g. when the application restarts).
 */
@Component
public class DataRepositories {

    private static final String DSL_TABLE = "order_dsl";
    private static final String SCRIPT_COLUMN = "script";

    private final DSLContext jooq;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public DataRepositories(
            DSLContext jooq,
            CustomerRepository customerRepo,
            ProductRepository productRepo,
            OrderRepository orderRepo
    ) {
        this.jooq = jooq;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public void persist(OrderDsl data) {
        persist(data, null);
    }

    @Transactional
    public void persist(OrderDsl data, String scriptName) {
        if (init(scriptName)) {
            data.get_customers().forEach(this::persistCustomer);
            data.get_products().forEach(this::persistProduct);
            data.get_orders().forEach(this::persistOrder);
        }
    }

    private Boolean init(String scriptName) {
        if (scriptName != null) {
            jooq.createTableIfNotExists(DSL_TABLE).column(SCRIPT_COLUMN, SQLDataType.CLOB.notNull()).execute();
            if (jooq.fetchCount(jooq.selectFrom(DSL_TABLE).where(field(SCRIPT_COLUMN).eq(scriptName))) > 0) {
                // If we have already persisted this script, abort.
                return false;
            }
            jooq.insertInto(table(DSL_TABLE)).values(scriptName).execute();
        }
        return true;
    }

    private void persistCustomer(CustomerDsl customer) {
        var customerId =
                customerRepo.create(new CustomerIn(customer.getCustomerId(), toStatus(customer.getStatus()), customer.getFirstName(), customer.getLastName()));
        customer.setId(customerId);
    }

    private se.callistaenterprise.kotlindsl.model.CustomerStatus toStatus(CustomerStatus status) {
        if (Active.class.equals(status.getClass())) {
            return se.callistaenterprise.kotlindsl.model.CustomerStatus.ACTIVE;
        } else {
            return se.callistaenterprise.kotlindsl.model.CustomerStatus.INACTIVE;
        }
    }

    private void persistProduct(ProductDsl product) {
        var productId = productRepo.create(new ProductIn(product.getProductId(), product.getProductName()));
        product.setId(productId);
    }

    private void persistOrder(CustomerOrderDsl order) {
        var orderId = orderRepo.create(
                new OrderIn(
                        order.getOrderId(),
                        new Customer(
                                order.getCustomer().getId(),
                                order.getCustomer().getCustomerId(),
                                toStatus(order.getCustomer().getStatus()),
                                order.getCustomer().getFirstName(),
                                order.getCustomer().getLastName()
                        ),
                        order.get_items().stream().map(item ->
                                        new Item(new Product(item.getProduct().getId(),
                                                item.getProduct().getProductId(),
                                                item.getProduct().getProductName()),
                                                item.getQuantity()))
                                .collect(Collectors.toList())
                )
        );
        order.setId(orderId);
    }

}
