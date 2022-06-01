package se.callistaenterprise.kotlindsl.repo;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.callistaenterprise.kotlindsl.model.Customer;
import se.callistaenterprise.kotlindsl.model.Item;
import se.callistaenterprise.kotlindsl.model.Order;
import se.callistaenterprise.kotlindsl.model.OrderIn;
import se.callistaenterprise.kotlindsl.model.Product;

import java.util.stream.Collectors;

import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;
import static se.callistaenterprise.kotlindsl.db.Tables.ITEM;
import static se.callistaenterprise.kotlindsl.db.Tables.ORDER;

@Repository
public class OrderRepository {

    private final DSLContext jooq;

    public OrderRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Order getById(long id) {
        return jooq.select(
                        ORDER.ID,
                        ORDER.ORDER_ID,
                        row(ORDER.customer().ID,
                                ORDER.customer().CUSTOMER_ID,
                                ORDER.customer().STATUS,
                                ORDER.customer().FIRST_NAME,
                                ORDER.customer().LAST_NAME).mapping(Customer::new),
                        multiset(
                                select(
                                        row(ITEM.product().ID,
                                                ITEM.product().PRODUCT_ID,
                                                ITEM.product().PRODUCT_NAME).mapping(Product::new),
                                        ITEM.QUANTITY
                                )
                                        .from(ITEM)
                                        .where(ITEM.ORDER_ID.eq(ORDER.ID))
                        ).convertFrom(r -> r.map(mapping(Item::new)))
                )
                .from(ORDER)
                .where(ORDER.ID.eq(id))
                .fetchOne(mapping(Order::new));
    }

    @Transactional
    public long create(OrderIn order) {
        long id = jooq.insertInto(ORDER, ORDER.ORDER_ID, ORDER.CUSTOMER_ID)
                .values(order.orderId(), order.customer().id())
                .returningResult(ORDER.ID).fetchOne().into(long.class);
        jooq.batchInsert(
                order.items().stream().map(item -> {
                            var itemRecord = jooq.newRecord(ITEM);
                            itemRecord.setOrderId(id);
                            itemRecord.setProductId(item.product().id());
                            itemRecord.setQuantity(item.quantity());
                            return itemRecord;
                        }
                ).collect(Collectors.toList())).execute();
        return id;
    }

}
