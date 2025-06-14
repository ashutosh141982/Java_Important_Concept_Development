package com.ashutosh.InvestmentOrder;

import java.util.*;

public class OrderBook {
    // Buy orders (bids) sorted descending
    private final TreeMap<Double, List<Order>> bids = new TreeMap<>(Collections.reverseOrder());

    // Sell orders (asks) sorted ascending
    private final TreeMap<Double, List<Order>> asks = new TreeMap<>();

    public void addOrder(Order order) {
        TreeMap<Double, List<Order>> book = (order.type == OrderType.BUY) ? bids : asks;
        book.computeIfAbsent(order.price, k -> new ArrayList<>()).add(order);
    }

    public void printOrderBook() {
        System.out.println("Order Book:");
        System.out.println("SELL:");
        for (Map.Entry<Double, List<Order>> entry : asks.entrySet()) {
            System.out.println(entry.getValue());
        }
        System.out.println("BUY:");
        for (Map.Entry<Double, List<Order>> entry : bids.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}