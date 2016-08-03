package com.ecoffee.ecoffee.model;

/**
 * Created by Vlade Ilievski on 7/22/2016.
 */
public class Table {
    private String name;
    private Order order;

    public Table(String name) {
        this.name = name;
        this.order = new Order();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return order.calculatePrice();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
