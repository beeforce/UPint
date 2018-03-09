package com.example.bee.upint2.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by Bee on 2/1/2018.
 */

public class AccessToken {

    @Json(name = "token_type")
    String tokenType;
    @Json(name = "expires_in")
    int expiresIn;
    @SerializedName("token")
    @Expose
    String accessToken;
    @Json(name = "refresh_token")
    String refreshToken;
    @SerializedName("id")
    @Expose
    String user_id;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("course_id")
    @Expose
    String course_id;
    @SerializedName("course_name")
    @Expose
    String course_name;
    @SerializedName("user_id")
    @Expose
    String user_id_addclass;
    @SerializedName("success")
    @Expose
    boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setcourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setUser_id_addclass(String user_id_addclass) {
        this.user_id_addclass = user_id_addclass;
    }


    public String getStatus() {
        return status;
    }

    public String getcourse_id() {
        return course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getUser_id_addclass() {
        return user_id_addclass;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

