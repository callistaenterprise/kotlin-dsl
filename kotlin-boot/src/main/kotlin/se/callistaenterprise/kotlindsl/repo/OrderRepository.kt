package se.callistaenterprise.kotlindsl.repo

import org.jooq.DSLContext
import org.jooq.Records.mapping
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.row
import org.jooq.impl.DSL.select
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import se.callistaenterprise.kotlindsl.db.Tables.ITEM
import se.callistaenterprise.kotlindsl.db.Tables.ORDER
import se.callistaenterprise.kotlindsl.model.Customer
import se.callistaenterprise.kotlindsl.model.Item
import se.callistaenterprise.kotlindsl.model.Order
import se.callistaenterprise.kotlindsl.model.OrderIn
import se.callistaenterprise.kotlindsl.model.Product

@Repository
class OrderRepository(private val jooq: DSLContext) {

    fun getById(id: Long): Order? {
        with(ORDER) {
            return jooq.select(
                ID,
                ORDER_ID,
                row(customer().ID, customer().CUSTOMER_ID, customer().STATUS, customer().FIRST_NAME, customer().LAST_NAME).mapping(::Customer),
                multiset(
                    select(
                        row(ITEM.product().ID, ITEM.product().PRODUCT_ID, ITEM.product().PRODUCT_NAME).mapping(::Product),
                        ITEM.QUANTITY
                    )
                        .from(ITEM)
                        .where(ITEM.ORDER_ID.eq(ORDER.ID))
                ).mapping(::Item)
            )
                .from(ORDER)
                .where(ORDER.ID.eq(id))
                .fetchOne(mapping(::Order))
        }
    }

    @Transactional
    fun create(order: OrderIn): Long {
        val id = jooq.newRecord(ORDER, order).apply {
            customerId = order.customer.id
            insert()
        }.id!!
        jooq.batchInsert(
            order.items.map { item ->
                jooq.newRecord(ITEM, item).apply {
                    orderId = id
                    productId = item.product.id
                }
            }
        ).execute()
        return id
    }

}
