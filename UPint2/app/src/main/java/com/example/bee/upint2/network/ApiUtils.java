package com.example.bee.upint2.network;

/**
 * Created by Bee on 2/5/2018.
 */

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "http://192.168.1.13:80/UPint/public/api/";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
