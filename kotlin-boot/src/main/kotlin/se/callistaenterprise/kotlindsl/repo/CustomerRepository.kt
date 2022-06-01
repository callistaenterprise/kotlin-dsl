package se.callistaenterprise.kotlindsl.repo

import org.jooq.DSLContext
import org.jooq.Records.mapping
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import se.callistaenterprise.kotlindsl.db.Tables.CUSTOMER
import se.callistaenterprise.kotlindsl.model.Customer
import se.callistaenterprise.kotlindsl.model.CustomerIn
import se.callistaenterprise.kotlindsl.model.CustomerStatus

@Repository
class CustomerRepository(private val jooq: DSLContext) {

    fun getById(id: Long): Customer? {
        with(CUSTOMER) {
            return jooq.select(ID, CUSTOMER_ID, STATUS, FIRST_NAME, LAST_NAME)
                .from(CUSTOMER)
                .where(CUSTOMER.ID.eq(id))
                .fetchOne(mapping(::Customer))
        }
    }

    @Transactional
    fun create(customer: CustomerIn): Long {
        val id = jooq.newRecord(CUSTOMER, customer).apply {
            status = CustomerStatus.ACTIVE
            insert()
        }.id!!
        return id
    }

}
