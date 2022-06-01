package se.callistaenterprise.kotlindsl.model;

import java.util.List;

public record Order(Long id, String orderId, Customer customer, List<Item> items) {
}
