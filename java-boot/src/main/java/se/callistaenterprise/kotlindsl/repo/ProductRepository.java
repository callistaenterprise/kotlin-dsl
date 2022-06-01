package se.callistaenterprise.kotlindsl.repo;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.callistaenterprise.kotlindsl.model.Product;
import se.callistaenterprise.kotlindsl.model.ProductIn;

import static org.jooq.Records.mapping;
import static se.callistaenterprise.kotlindsl.db.Tables.PRODUCT;

@Repository
public class ProductRepository {

    private final DSLContext jooq;

    public ProductRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Product getById(long id) {
        return jooq.select(PRODUCT.ID, PRODUCT.PRODUCT_ID, PRODUCT.PRODUCT_NAME)
                .from(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOne(mapping(Product::new));
    }

    @Transactional
    public long create(ProductIn product) {
        return jooq.insertInto(PRODUCT, PRODUCT.PRODUCT_ID, PRODUCT.PRODUCT_NAME)
                .values(product.productId(), product.productName())
                .returningResult(PRODUCT.ID).fetchOne().into(long.class);
    }

}
