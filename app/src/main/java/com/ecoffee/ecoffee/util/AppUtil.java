package com.ecoffee.ecoffee.util;

import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class AppUtil {
    private static final List<User> employees = new ArrayList<>();
    private static final List<Table> tables = new ArrayList<>();

    static {
        employees.add(new User("ile", "ile123"));
        employees.add(new User("vlade", "vlade123"));
        employees.add(new User("admin", "admin123"));

        tables.add(new Table("Table 1 "));
        tables.add(new Table("Table 2 "));
        tables.add(new Table("Table 3 "));
        tables.add(new Table("Table 4 "));
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
}
