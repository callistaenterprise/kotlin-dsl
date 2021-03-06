/*
 * This file is generated by jOOQ.
 */
package se.callistaenterprise.kotlindsl.db.enums;


import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

import se.callistaenterprise.kotlindsl.db.Public;


/**
 * This class is generated by jOOQ.
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
public enum CustomerStatus implements EnumType {

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    private final String literal;

    private CustomerStatus(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "customer_status";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal
     */
    public static CustomerStatus lookupLiteral(String literal) {
        return EnumType.lookupLiteral(CustomerStatus.class, literal);
    }
}
