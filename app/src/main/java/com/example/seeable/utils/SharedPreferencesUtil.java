package com.example.seeable.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.seeable.model.Child;
import com.example.seeable.model.User;


public class SharedPreferencesUtil {

    private static final String PREF_NAME = "com.example.testapp.PREFERENCE_FILE_KEY";

    private static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    private static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Add more methods for other data types as needed

    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    private static boolean contains(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    // Add more utility methods as needed

    // User related methods
    public static void saveUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getId());
        editor.putString("fname", user.getFname());
        editor.putString("lname", user.getLname());
        editor.putString("phone", user.getPhone());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("position", user.getPosition());
        editor.putBoolean("isAdmin", user.isAdmin());
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // check if user is logged in
        if (!isUserLoggedIn(context)) {
            return null;
        }
        String id = sharedPreferences.getString("id", "");
        String fname = sharedPreferences.getString("fname", "");
        String lname = sharedPreferences.getString("lname","");
        String phone = sharedPreferences.getString("phone","");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        String position = sharedPreferences.getString("position", User.Position.Normal.getType());
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        return new User(id,fname,lname,phone,email,password,position,isAdmin);
    }

    public static void signOutUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("id");
        editor.remove("fname");
        editor.remove("lname");
        editor.remove("fname");
        editor.remove("email");
        editor.remove("password");
        editor.remove("position");
        editor.remove("isAdmin");
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        return contains(context, "id");
    }

}

