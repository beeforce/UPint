package com.example.bee.upint2.network;



import com.example.bee.upint2.model.UserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Bee on 2/1/2018.
 */

public interface ApiService {


    @POST("register")
    @Multipart
    Call<AccessToken> register(@Part("first_name") RequestBody first_name, @Part("last_name") RequestBody RequestBody, @Part("email") RequestBody email,
                               @Part("password") RequestBody password, @Part("c_password") RequestBody repassword, @Part("phone_number") RequestBody phonenumber,
                               @Part("school") RequestBody school, @Part("major") RequestBody major, @Part("state") RequestBody state,
                               @Part("Date_graduated") RequestBody date_gradated, @Part MultipartBody.Part photo);

//    @POST("register")
//    @FormUrlEncoded
//    Call<AccessToken> register(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email,
//                               @Field("password") String password, @Field("c_password") String repassword, @Field("phone_number") String phonenumber,
//                               @Field("school") String school, @Field("major") String major, @Field("state") String state,
//                               @Field("Date_graduated") String date_gradated, @Field("image") String image);

//    @POST("login")
//    @FormUrlEncoded
//    Call<AccessToken> login(@Field("email") String email, @Field("password") String password);



    @POST("login")
    @Multipart
    Call<AccessToken> login(@Part("email") RequestBody email, @Part("password") RequestBody password);


    //ยังไม่เสด
    @GET("userDetails/")
    Call<UserProfile> userDetail(@Query("email") String email);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

}
