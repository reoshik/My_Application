package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class UserStaticInfo {

    public static final List<User> userList = new ArrayList<>();

    public static int selectedPosition = -1;

    public UserStaticInfo() {

        if (userList.isEmpty()) {

            userList.add(new User(
                    "reoshi", "спит", 19, 0));

            userList.add(new User(
                    "reoshi", "в сети", 19, 1));

            userList.add(new User(
                    "reoshi", "не беспокоить", 19, 2));

            userList.add(new User(
                    "reoshi", "где яЫ", 19, 0));
        }
    }
}