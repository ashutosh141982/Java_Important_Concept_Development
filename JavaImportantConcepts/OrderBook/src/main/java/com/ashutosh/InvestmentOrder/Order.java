package com.ashutosh.InvestmentOrder;

enum OrderType { BUY, SELL }

class Order {
    String id;
    OrderType type;
    double price;
    int quantity;

    public Order(String id, OrderType type, double price, int quantity) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "[" + type + " " + price + " x " + quantity + "]";
    }
}
