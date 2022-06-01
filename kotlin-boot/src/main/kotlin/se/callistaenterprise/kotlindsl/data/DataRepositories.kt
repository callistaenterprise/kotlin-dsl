package se.callistaenterprise.kotlindsl.data

import org.jooq.DSLContext
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import se.callistaenterprise.kotlindsl.dsl.Active
import se.callistaenterprise.kotlindsl.dsl.CustomerDsl
import se.callistaenterprise.kotlindsl.dsl.CustomerOrderDsl
import se.callistaenterprise.kotlindsl.dsl.CustomerStatus
import se.callistaenterprise.kotlindsl.dsl.Inactive
import se.callistaenterprise.kotlindsl.dsl.OrderDsl
import se.callistaenterprise.kotlindsl.dsl.ProductDsl
import se.callistaenterprise.kotlindsl.model.Customer
import se.callistaenterprise.kotlindsl.model.CustomerIn
import se.callistaenterprise.kotlindsl.model.Item
import se.callistaenterprise.kotlindsl.model.OrderIn
import se.callistaenterprise.kotlindsl.model.Product
import se.callistaenterprise.kotlindsl.model.ProductIn
import se.callistaenterprise.kotlindsl.repo.CustomerRepository
import se.callistaenterprise.kotlindsl.repo.OrderRepository
import se.callistaenterprise.kotlindsl.repo.ProductRepository

const val DSL_TABLE = "order_dsl"
const val SCRIPT_COLUMN = "script"

/*
   Component responsible for persisting an instance of OrderDsl to the database. The OrdeDsl instance is traversed from root
   to bottom, and each entity found is persisted using the business logic repositories.

   The name of the script that has been used to create the OrderDsl is also saved in the table "order_dsl", so that the same
   script will not be persisted twice (e.g. when the application restarts).
 */
@Component
class DataRepositories(
    val jooq: DSLContext,
    val customerRepo: CustomerRepository,
    val productRepo: ProductRepository,
    val orderRepo: OrderRepository,
) {

    @Transactional
    fun persist(block: OrderDsl.() -> Unit) {
        persist(OrderDsl().apply(block))
    }

    @Transactional
    fun persist(data: OrderDsl, scriptName: String? = null) {
        if (init(scriptName)) {
            data._customers.map { persistCustomer(it) }
            data._products.map { persistProduct(it) }
            data._orders.map { persistOrder(it) }
        }
    }

    private fun init(scriptName: String?): Boolean {
        if (scriptName != null) {
            jooq.createTableIfNotExists(DSL_TABLE).column(SCRIPT_COLUMN, SQLDataType.CLOB.notNull()).execute()
            // If we have already persisted this script, abort.
            if (jooq.fetchCount(jooq.selectFrom(DSL_TABLE).where(field(SCRIPT_COLUMN).eq(scriptName))) > 0) return false
            jooq.insertInto(table(DSL_TABLE)).values(scriptName).execute()
        }
        return true
    }

    private fun persistCustomer(customer: CustomerDsl) {
        val customerId =
            customerRepo.create(CustomerIn(customer.customerId, toStatus(customer.status), customer.firstName, customer.lastName))
        customer.id = customerId
    }

    private fun toStatus(status: CustomerStatus): se.callistaenterprise.kotlindsl.model.CustomerStatus =
        when (status) {
            Active -> se.callistaenterprise.kotlindsl.model.CustomerStatus.ACTIVE
            Inactive -> se.callistaenterprise.kotlindsl.model.CustomerStatus.INACTIVE
        }

    private fun persistProduct(product: ProductDsl) {
        val productId = productRepo.create(ProductIn(product.productId, product.productName))
        product.id = productId
    }

    private fun persistOrder(order: CustomerOrderDsl) {
        val orderId = orderRepo.create(
            OrderIn(
                order.orderId,
                Customer(
                    order.customer.id,
                    order.customer.customerId,
                    toStatus(order.customer.status),
                    order.customer.firstName,
                    order.customer.lastName
                ),
                order._items.map { Item(Product(it.product.id, it.product.productId, it.product.productName), it.quantity) }
            )
        )
        order.id = orderId
    }

}
