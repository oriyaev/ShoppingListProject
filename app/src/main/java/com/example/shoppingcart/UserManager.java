package com.example.shoppingcart;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_CURRENT_USER = "current_user";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        if (prefs == null)
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean registerUser(String username, String password, String phone) {
        if (username.isEmpty() || password.isEmpty()) return false;
        prefs.edit().putString(username, password + "," + phone).apply();
        return true;
    }

    public static boolean loginUser(String username, String password) {
        if (!prefs.contains(username)) return false;
        String[] parts = prefs.getString(username, ",").split(",");
        if (parts.length > 0 && parts[0].equals(password)) {
            prefs.edit().putString(KEY_CURRENT_USER, username).apply();
            return true;
        }
        return false;
    }

    public static boolean isUserLoggedIn() {
        return prefs.contains(KEY_CURRENT_USER);
    }

    public static String getCurrentUser() {
        return prefs.getString(KEY_CURRENT_USER, "");
    }

    public static void logoutUser() {
        prefs.edit().remove(KEY_CURRENT_USER).apply();
    }
}
