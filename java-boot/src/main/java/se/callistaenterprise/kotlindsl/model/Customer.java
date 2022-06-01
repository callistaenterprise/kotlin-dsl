package se.callistaenterprise.kotlindsl.model;

public record Customer(Long id, String customerId, CustomerStatus status, String firstName, String lastName) {
}
