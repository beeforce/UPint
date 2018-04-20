package com.example.bee.upint2.network;



import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Course_user;
import com.example.bee.upint2.model.UserProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bee on 2/1/2018.
 */

public interface ApiService {


    @POST("register")
    @Multipart
    Call<AccessToken> register(@Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("email") RequestBody email,
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


    @POST("addClass")
    @Multipart
    Call<AccessToken> addClass(@Part("course_name") RequestBody course_name, @Part("date") RequestBody date, @Part("description") RequestBody description,
                               @Part("start_time") RequestBody start_time, @Part("finish_time") RequestBody finish_time, @Part("user_id") RequestBody user_id,
                               @Part("price_per_student") RequestBody price_per_student, @Part("target_university") RequestBody target_university,
                               @Part("target_major") RequestBody target_major, @Part("target_years") RequestBody target_years, @Part("terms") RequestBody terms,
                               @Part("level_of_difficult") RequestBody level_of_difficult, @Part("total_student") RequestBody total_student,
                               @Part("tags") RequestBody tags, @Part("place") RequestBody place, @Part MultipartBody.Part photo);


    @POST("login")
    @Multipart
    Call<AccessToken> login(@Part("email") RequestBody email, @Part("password") RequestBody password, @Part("state") RequestBody usertype);

    @GET("Allcoursedetail")
    Call<List<Course>> getCoursedetail();

    @GET("getAllcoursedetail")
    Call<List<Course>> getAllCoursedetail();


    @GET("userDetailswithId/{id}")
    Call<UserProfile> userDetailswithId(@Path("id") String id);

    @GET("classCount/{course_id}")
    Call<UserProfile> classCount(@Path("course_id") String course_id);



    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @POST("bookClass")
    @Multipart
    Call<AccessToken> bookClass(@Part("user_id") RequestBody user_id, @Part("course_id") RequestBody course_id);

    @GET("courseEnroll/{user_id}")
    Call<List<Course_user>> searchclassEnroll(@Path("user_id") int user_id);

    @GET("courseListening/{user_id}")
    Call<List<Course>> courseListening(@Path("user_id") int user_id);

    @GET("courseDetails/{course_id}")
    Call<Course> courseDetailswithId(@Path("course_id") String course_id);

    @GET("CheckEmailRegister/{email}")
    Call<AccessToken> checkUserEmailforRegister(@Path("email") String email);

    @POST("addSearchRecent")
    @Multipart
    Call<AccessToken> updateSearchRecent(@Part("id") RequestBody user_id, @Part("search_recent") RequestBody search_data);

    @GET("getSerchRecent/{user_id}")
    Call<AccessToken> getSearchRecent(@Path("user_id") String user_id);

    @GET("logout")
    Call<AccessToken> Logout(@Query("token") String token);

    @POST("addCommenttoFeedback")
    @Multipart
    Call<AccessToken> addCommenttoFeedback(@Part("user_id") RequestBody user_id, @Part("comment") RequestBody comment);

    @GET("getCommentfromFeedback")
    Call<AccessToken> getCommentfromFeedback(@Query("user_id") String user_id);


}
