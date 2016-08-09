package com.ecoffee.ecoffee.util;

import com.ecoffee.ecoffee.model.Order;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class AppUtil {
    public static final double LOGIN_EXPIRATION_TIME_MIN =0.25;

    private static final List<User> employees = new ArrayList<>();
    private static final List<Table> tables = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();

    static {
        employees.add(new User("ile", "ile123"));
        employees.add(new User("vlade", "vlade123"));
        employees.add(new User("admin", "admin123"));

        tables.add(new Table("Table 1 "));
        tables.add(new Table("Table 2 "));
        tables.add(new Table("Table 3 "));
        tables.add(new Table("Table 4 "));

        products.add(new Product(1, 2.5, "Capucino"));
        products.add(new Product(2, 1.5, "Fredo"));
        products.add(new Product(3, 3.0, "Machiato"));
        products.add(new Product(4, 2.0, "Nescafe"));
        products.add(new Product(5, 4.5, "Protein shake"));
        products.add(new Product(6, 2.0, "Espresso"));
        products.add(new Product(7, 1.0, "Turkish coffee"));
        products.add(new Product(8, 4.5, "Frape"));
        products.add(new Product(9, 2.0, "Coca cola"));
        products.add(new Product(10, 1.5, "Water"));
        products.add(new Product(11, 3.0, "Irish coffee"));

    }

    public static void addTable(Table table) {
        tables.add(table);
    }


    public static void deleteTable(Table table) {
        tables.remove(table);
    }

    public static List<Table> getTables() {
        return tables;
    }

    public static List<User> getEmployees() {
        return employees;
    }

    public static boolean checkUserExist(String username, String password) {
        for (User user : AppUtil.getEmployees()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                return true;
            }
        }
        return false;
    }

    public static List<Product> getProducts() {
        return products;
    }

}

