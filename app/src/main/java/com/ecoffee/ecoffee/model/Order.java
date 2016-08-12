package com.ecoffee.ecoffee.model;

import android.app.ActionBar;
import android.view.View;

import com.ecoffee.ecoffee.util.AppPreferences;
import com.ecoffee.ecoffee.util.AppUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ilija Angeleski on 7/22/2016.
 */
public class Order implements Serializable {

    private boolean paid = false;

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

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void deleteOrder() {
        products.clear();
    }

    public double calculatePrice() {
        double totalPrice = 0;
        for (Product product : products) {
            //totalPrice = totalPrice + product.getPrice();
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public Map<String, Integer> countProductInTable() {
        Map<String, Integer> map = new HashMap<>();
        for (Product p : products) {
            int tempCount = map.get(p.getName()) != null ? map.get(p.getName()) : 0;
            map.put(p.getName(), tempCount + 1);
        }
        return map;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "paid=" + paid +
                ", products=" + products +
                '}';
    }
}

