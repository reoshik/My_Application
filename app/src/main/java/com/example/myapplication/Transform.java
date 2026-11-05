package com.example.myapplication;

public class Transform {

    public static int parseIntOrDefault(
            String whatParse,
            int defaultValue) {

        try {
            return Integer.parseInt(whatParse);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}