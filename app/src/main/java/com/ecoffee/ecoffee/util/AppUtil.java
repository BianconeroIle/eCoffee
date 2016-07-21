package com.ecoffee.ecoffee.util;

import com.ecoffee.ecoffee.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class AppUtil {
    private static final List<User> employees = new ArrayList<>();

    static {
        employees.add(new User("ile", "ile123"));
        employees.add(new User("vlade", "vlade123"));
        employees.add(new User("admin", "admin123"));
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
