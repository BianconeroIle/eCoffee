package com.ecoffee.ecoffee.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.ui.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlade Ilievski on 8/8/2016.
 */
public class AppPreferences {
    public static final String TAG = AppPreferences.class.getName();

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

    public boolean isAuthorised() {
        boolean authorised = false;
        Date currentDate = new Date();
        Date loggingDate = new Date(getExpirationDate());
        authorised = compareAuthorizationDates(currentDate, loggingDate);
        Log.d(TAG, "check if user is authorized, isAuthorized=" + authorised);
        return authorised;
    }

    private boolean compareAuthorizationDates(Date d1, Date d2) {
        long passedTimeInMilliseconds = d1.getTime() - d2.getTime();
        Log.d(TAG, "compareAuthorizationDates, passedTime in min=" + ((passedTimeInMilliseconds / (1000 * 60)) % 60));
        if (passedTimeInMilliseconds >= (AppUtil.LOGIN_EXPIRATION_TIME_MIN * 60 * 1000)) {
            return false;
        }
        return true;
    }

    public boolean checkIsAuthenticatedAndLogout() {
        if (!isAuthorised()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.putExtra("loginExpired", true);
            context.startActivity(i);
            return false;
        }
        return true;
    }

    public void saveTables(List<Table> tables) {
        Log.d("AppPreferences", "saveTables()");
        Gson gson = new Gson();
        editor.putString("app.tables", gson.toJson(tables));
        editor.commit();
    }

    public List<Table> getSavedTables() {
        Log.d("AppPreferences", "getSaveTables()");
        Gson gson = new Gson();
        String jsonString = sp.getString("app.tables", "");
        if (!"".equals(jsonString)) {
            Type collectionType = new TypeToken<List<Table>>() {
            }.getType();

            List<Table> listObj = gson.fromJson(jsonString, collectionType);
            return listObj;
        }
        return Collections.emptyList();
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }
}

