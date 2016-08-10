package com.ecoffee.ecoffee.util;

import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class AppUtil {
    public static final double LOGIN_EXPIRATION_TIME_MIN = 3600;

    private static final List<User> employees = new ArrayList<>();
    private static final List<Table> tables = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();

    static {
        employees.add(new User("ile", "ile123"));
        employees.add(new User("vlade", "vlade123"));
        employees.add(new User("admin", "admin123"));

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

    public static void addSavedTables(List<Table> savedTables) {
        tables.addAll(savedTables);
    }

    public static void generateData() {
        tables.add(new Table("Table 1 "));
        tables.add(new Table("Table 2 "));
        tables.add(new Table("Table 3 "));
        tables.add(new Table("Table 4 "));
    }

    public static void addTable(Table table, AppPreferences preferences) {
        tables.add(table);

        if (preferences != null) {
            preferences.saveTables(getTables());
        }
    }


    public static void deleteTable(Table table, AppPreferences preferences) {
        tables.remove(table);
        if (preferences != null) {
            preferences.saveTables(getTables());
        }
    }

    public static void onRefreshTable(Table table, AppPreferences preferences) {
        table.getOrder().isPaid();
        if (preferences != null) {
            preferences.saveTables(AppUtil.getTables());
        }
    }


    public static void addOrderOnTable(AppPreferences preferences, Table table, Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            table.getOrder().addOrder(product);
        }
        if (preferences != null) {
            preferences.saveTables(getTables());
        }
    }

    public static void deleteProduct(Table table, Product product, AppPreferences preferences) {
        table.getOrder().deleteProduct(product);
        if (preferences != null) {
            preferences.saveTables(AppUtil.getTables());
        }
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

