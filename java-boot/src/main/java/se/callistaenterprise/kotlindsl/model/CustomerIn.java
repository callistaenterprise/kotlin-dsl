package se.callistaenterprise.kotlindsl.model;

public record CustomerIn(String customerId, CustomerStatus status, String firstName, String lastName) {
}
