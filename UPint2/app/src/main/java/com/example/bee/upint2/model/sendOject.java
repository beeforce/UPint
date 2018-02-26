package com.example.bee.upint2.model;

/**
 * Created by Bee on 2/15/2018.
 */

public class sendOject {
    public String getAccesstoken() {
        return accesstoken;
    }

    public String getUser_id() {
        return user_id;
    }

    private static String accesstoken;

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private static String user_id;
}
