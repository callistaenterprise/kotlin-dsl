package se.callistaenterprise.kotlindsl.model;

import java.util.List;

public record OrderIn(String orderId, Customer customer, List<Item> items) {
}
