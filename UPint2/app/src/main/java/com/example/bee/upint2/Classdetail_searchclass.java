package com.example.bee.upint2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Classdetail_searchclass extends AppCompatActivity {

    private ApiService mAPIService;
    private String user_id, course_id, image_path;
    private TextView numberofstudent_searchclass, teacher, course_name, price_searchclass
            , numberofstudent_searchclass2, place_searchclass, term_searchclass, description_searchclass, target_searchclass;
    private Button buttontags1_searchclass, buttontags2_searchclass, buttontags3_searchclass, buttontags4_searchclass, buttontags5_searchclass;
    private ImageView class_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail_searchclass);

        mAPIService = ApiUtils.getAPIService();

        //set textview
        numberofstudent_searchclass = findViewById(R.id.numberofstudent_searchclass);
        teacher = findViewById(R.id.teacher);
        course_name = findViewById(R.id.course_name);
        price_searchclass = findViewById(R.id.price_searchclass);
        numberofstudent_searchclass2 = findViewById(R.id.numberofstudent_searchclass2);
        place_searchclass = findViewById(R.id.place_searchclass);
        buttontags1_searchclass = findViewById(R.id.buttontags1_searchclass);
        buttontags2_searchclass = findViewById(R.id.buttontags2_searchclass);
        buttontags3_searchclass = findViewById(R.id.buttontags3_searchclass);
        buttontags4_searchclass = findViewById(R.id.buttontags4_searchclass);
        buttontags5_searchclass = findViewById(R.id.buttontags5_searchclass);
        term_searchclass = findViewById(R.id.term_searchclass);
        description_searchclass = findViewById(R.id.description_searchclass);
        target_searchclass = findViewById(R.id.target_searchclass);
        class_picture = findViewById(R.id.class_picture);

        //get extra string
        user_id = getIntent().getStringExtra("user_id");
        course_id = getIntent().getStringExtra("course_id");
        course_name.setText(getIntent().getStringExtra("course_name"));
        price_searchclass.setText(getIntent().getStringExtra("cost"));
        numberofstudent_searchclass2.setText(getIntent().getStringExtra("totalstudent"));
        place_searchclass.setText(getIntent().getStringExtra("place"));
        ArrayList<String> taglist = new ArrayList<>();

        String tag = getIntent().getStringExtra("tags");
        String[] tagpart = tag.split(",");
        for (int i=0; i < tagpart.length;i++){
            taglist.add(tagpart[i]);
        }


        if (tagpart.length == 1){
            buttontags2_searchclass.setVisibility(View.GONE);
            buttontags3_searchclass.setVisibility(View.GONE);
            buttontags4_searchclass.setVisibility(View.GONE);
            buttontags5_searchclass.setVisibility(View.GONE);
            buttontags1_searchclass.setText(taglist.get(0));
        }
        else if (tagpart.length == 2){
            buttontags3_searchclass.setVisibility(View.GONE);
            buttontags4_searchclass.setVisibility(View.GONE);
            buttontags5_searchclass.setVisibility(View.GONE);
            buttontags1_searchclass.setText(taglist.get(0));
            buttontags2_searchclass.setText(taglist.get(1));
        }else {
            buttontags1_searchclass.setText(taglist.get(0));
            buttontags2_searchclass.setText(taglist.get(1));
            buttontags3_searchclass.setText(taglist.get(2));

        }
//        buttontags1_searchclass.setText(getIntent().getStringExtra("tags"));
        term_searchclass.setText(getIntent().getStringExtra("term"));
        description_searchclass.setText(getIntent().getStringExtra("description"));
        target_searchclass.setText(getIntent().getStringExtra("target_year")+" year student0");
        image_path = getIntent().getStringExtra("image_path");

        String[] parts = image_path.split("/");
        String part1 = parts[0];
        String part4 = parts[3];
        String part5 = parts[4];
        String part6 = parts[5];
        String part7 = parts[6];
        String part8 = parts[7];
        String part9 = parts[8];
        String url_image = part1 + "//192.168.1.13/" + part4 + "/" + part5 + "/" + part6 + "/" + part7 + "/" + part8 + "/" + part9;
        Glide.with(getApplicationContext())
                .load(url_image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        android.util.Log.w("GLIDE", String.format(Locale.ROOT,
                                "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        android.util.Log.w("GLIDE", String.format(Locale.ROOT,
                                "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
                        return false;
                    }
                })
                .centerCrop()
                .into(class_picture);

        getClasscount();
        getTeachername();
    }
    public void getClasscount(){
        mAPIService.classCount(course_id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                numberofstudent_searchclass.setText(response.body().getCount()+" /");

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    public void getTeachername(){
        mAPIService.userDetailswithId(user_id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                teacher.setText(response.body().getFirst_name());

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });

    }
}
