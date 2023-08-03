package com.example.licious.authentication;

import android.text.TextUtils;

public class common {
    public static String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static String Password = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$";

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && target.matches(EmailPattern));
    }
    public static boolean isValidPassword(String password) {
        return (!TextUtils.isEmpty(password) && password.matches(Password));
    }
}
