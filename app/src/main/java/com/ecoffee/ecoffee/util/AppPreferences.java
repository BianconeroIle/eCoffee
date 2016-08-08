package com.ecoffee.ecoffee.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by Vlade Ilievski on 8/8/2016.
 */
public class AppPreferences {
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public AppPreferences(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences(
                "eCoffee.preferences", Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public void setUserName(String userName) {
        editor.putString("app.username", userName);
        editor.commit();
    }

    public String getUserName() {
        return sp.getString("app.username", "");
    }

    public void setExpirationDate(Date date) {
        editor.putLong("app.expirationDate", date.getTime());
        editor.commit();
    }

    public long getExpirationDate() {
        return sp.getLong("app.expirationDate", 0);
    }

    public void setUserLogged(boolean logged) {
        editor.putBoolean("app.logged", logged);
        editor.commit();
    }

    public boolean isUserLogged() {
        return sp.getBoolean("app.logged", false);
    }
}
