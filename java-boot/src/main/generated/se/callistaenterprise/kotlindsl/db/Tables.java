/*
 * This file is generated by jOOQ.
 */
package se.callistaenterprise.kotlindsl.db;


import javax.annotation.processing.Generated;

import se.callistaenterprise.kotlindsl.db.tables.Customer;
import se.callistaenterprise.kotlindsl.db.tables.Item;
import se.callistaenterprise.kotlindsl.db.tables.Order;
import se.callistaenterprise.kotlindsl.db.tables.Product;


/**
 * Convenience access to all tables in public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.16.6",
        "schema version:none"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.customer</code>.
     */
    public static final Customer CUSTOMER = Customer.CUSTOMER;

    /**
     * The table <code>public.item</code>.
     */
    public static final Item ITEM = Item.ITEM;

    /**
     * The table <code>public.order</code>.
     */
    public static final Order ORDER = Order.ORDER;

    /**
     * The table <code>public.product</code>.
     */
    public static final Product PRODUCT = Product.PRODUCT;
}
