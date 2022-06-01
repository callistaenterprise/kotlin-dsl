@file:Suppress("NonAsciiCharacters")

package se.callistaenterprise.kotlindsl.dsl

@DslMarker
annotation class OrderDslMarker

@OrderDslMarker
class OrderDsl {

    // The reason why these members (and similarly named members in other classes below) are named with an initial underscore is
    // to make them less likely to be offered by the IDE auto-completion when using the dsl. They should not be accessed directly
    // when writing dsl code, but are accessible when the resulting instance is used.
    val _products = mutableListOf<ProductDsl>()
    val _customers = mutableListOf<CustomerDsl>()
    val _orders = mutableListOf<CustomerOrderDsl>()

    fun product(productName: String, block: ProductDsl.() -> Unit): ProductDsl {
        val product = ProductDsl(this, productName).apply(block)
        _products.add(product)
        return product
    }

    fun product(productName: String) = product(productName) {}

    fun customer(customerId: String = Data.randomId(), block: CustomerDsl.() -> Unit): CustomerDsl {
        val customer = CustomerDsl(this, customerId).apply(block)
        _customers.add(customer)
        return customer
    }

    fun customer(customerId: String = Data.randomId()): CustomerDsl = customer(customerId) {}

    fun order(customer: CustomerDsl, orderId: String = Data.randomId(), block: CustomerOrderDsl.() -> Unit): CustomerOrderDsl {
        val order = CustomerOrderDsl(this, customer, orderId).apply(block)
        _orders.add(order)
        return order
    }
}

@OrderDslMarker
class CustomerDsl(val dsl: OrderDsl, val customerId: String) {
    var id = -1L
    var status: CustomerStatus = Active
    var firstName = Data.firstName()
    var lastName = Data.lastName()
}

sealed interface CustomerStatus
object Active : CustomerStatus
object Inactive : CustomerStatus

@OrderDslMarker
class ProductDsl(val orderDsl: OrderDsl, val productName: String, val productId: String = Data.randomId()) {
    var id = -1L
}

@OrderDslMarker
class CustomerOrderDsl(val orderDsl: OrderDsl, val customer: CustomerDsl, val orderId: String = Data.randomId()) {
    var id = -1L
    val _items = mutableListOf<ItemDsl>()

    fun item(product: ProductDsl, quantity: Long = 1) {
        _items.add(ItemDsl(this, product, quantity))
    }
}

@OrderDslMarker
class ItemDsl(val orderDsl: CustomerOrderDsl, val product: ProductDsl, val quantity: Long) {
    var id = -1L
}
