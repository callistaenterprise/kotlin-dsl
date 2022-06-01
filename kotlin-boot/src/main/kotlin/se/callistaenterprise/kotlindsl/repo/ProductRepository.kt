package se.callistaenterprise.kotlindsl.repo

import org.jooq.DSLContext
import org.jooq.Records.mapping
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import se.callistaenterprise.kotlindsl.db.Tables.PRODUCT
import se.callistaenterprise.kotlindsl.model.Product
import se.callistaenterprise.kotlindsl.model.ProductIn

@Repository
class ProductRepository(private val jooq: DSLContext) {

    fun getById(id: Long): Product? {
        with(PRODUCT) {
            return jooq.select(ID, PRODUCT_ID, PRODUCT_NAME)
                .from(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOne(mapping(::Product))
        }
    }

    @Transactional
    fun create(product: ProductIn): Long {
        val id = jooq.newRecord(PRODUCT, product).apply {
            insert()
        }.id!!
        return id
    }

}
