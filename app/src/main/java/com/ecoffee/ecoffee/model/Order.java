package com.ecoffee.ecoffee.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/22/2016.
 */
public class Order {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addOrder(Product product) {
        products.add(product);
    }

    public void deleteOrder(Product product) {
        products.remove(product);
    }

    public double calculatePrice() {
        double totalPrice = 0;
        for (Product product : products) {
            //totalPrice = totalPrice + product.getPrice();
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

}

