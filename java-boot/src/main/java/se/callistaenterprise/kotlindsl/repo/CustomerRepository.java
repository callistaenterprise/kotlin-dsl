package se.callistaenterprise.kotlindsl.repo;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.callistaenterprise.kotlindsl.model.Customer;
import se.callistaenterprise.kotlindsl.model.CustomerIn;

import static org.jooq.Records.mapping;
import static se.callistaenterprise.kotlindsl.db.Tables.CUSTOMER;

@Repository
public class CustomerRepository {

    private final DSLContext jooq;

    public CustomerRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Customer getById(long id) {
        return jooq.select(CUSTOMER.ID, CUSTOMER.CUSTOMER_ID, CUSTOMER.STATUS, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME)
                .from(CUSTOMER)
                .where(CUSTOMER.ID.eq(id))
                .fetchOne(mapping(Customer::new));
    }

    @Transactional
    public long create(CustomerIn customer) {
        return jooq.insertInto(CUSTOMER, CUSTOMER.CUSTOMER_ID, CUSTOMER.STATUS, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME)
                .values(customer.customerId(), customer.status(), customer.firstName(), customer.lastName())
                .returningResult(CUSTOMER.ID).fetchOne().into(long.class);
    }

}
