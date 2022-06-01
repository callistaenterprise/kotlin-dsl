package se.callistaenterprise.kotlindsl.model

enum class CustomerStatus { ACTIVE, INACTIVE }

data class Customer(val id: Long, val customerId: String, val status: CustomerStatus, val firstName: String, val lastName: String)

data class CustomerIn(val customerId: String, val status: CustomerStatus, val firstName: String, val lastName: String)

data class Product(val id: Long, val productId: String, val productName: String)

data class ProductIn(val productId: String, val productName: String)

data class Order(val id: Long, val orderId: String, val customer: Customer, val items: List<Item>)

data class OrderIn(val orderId: String, val customer: Customer, val items: List<Item>)

data class Item(val product: Product, val quantity: Long)
