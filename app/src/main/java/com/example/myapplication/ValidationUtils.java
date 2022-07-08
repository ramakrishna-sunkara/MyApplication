package com.example.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    // "(?=.*[@#$%^&+=])" +   //at least 1 special character
    // "(?=\\S+$)" +          //no white spaces
    private static final String PASSWORD_PATTERN = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            ".{10,}" +              //at least 10 characters
            "$";

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
