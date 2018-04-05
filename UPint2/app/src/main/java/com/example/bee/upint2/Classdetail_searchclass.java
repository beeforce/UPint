package com.example.bee.upint2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.model.sendOject;
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
    private TextView numberofstudent_searchclass, teacher, course_name, price_searchclass, numberofstudent_searchclass2, place_searchclass, description_searchclass, target_searchclass, classdetailcourse_name,
            levelofdifficult, costclassdetail, classdetailschedule, classdetailscheduletime, classdetailplace, termclassdetail, numberclassdetail;
    private Button buttontags1_searchclass, buttontags2_searchclass, buttontags3_searchclass, book;
    private ImageView class_picture;
    private ImageView back, back2;
    private RelativeLayout layout1;
    private LinearLayout layout2;

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
        description_searchclass = findViewById(R.id.description_searchclass);
        target_searchclass = findViewById(R.id.target_searchclass);
        class_picture = findViewById(R.id.class_picture);

        classdetailcourse_name = (TextView) findViewById(R.id.classdetailcourse_name);
        levelofdifficult = (TextView) findViewById(R.id.levelofdifficult);
        costclassdetail = (TextView) findViewById(R.id.costclassdetail);
        classdetailschedule = (TextView) findViewById(R.id.classdetailschedule);
        classdetailscheduletime = (TextView) findViewById(R.id.classdetailscheduletime);
        classdetailplace = (TextView) findViewById(R.id.classdetailplace);
        termclassdetail = (TextView) findViewById(R.id.termclassdetail);
        numberclassdetail = (TextView) findViewById(R.id.numberclassdetail);

        classdetailcourse_name.setText(getIntent().getStringExtra("course_name"));
        levelofdifficult.setText(getIntent().getStringExtra("level"));
        costclassdetail.setText(getIntent().getStringExtra("cost") + "B");
        classdetailschedule.setText(getIntent().getStringExtra("scheduledate"));
        classdetailscheduletime.setText(getIntent().getStringExtra("scheduletime"));
        classdetailplace.setText(getIntent().getStringExtra("place"));
        termclassdetail.setText(getIntent().getStringExtra("term"));
        numberclassdetail.setText(getIntent().getStringExtra("totalstudent"));
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);

        back = (ImageView) findViewById(R.id.backclassdetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back2 = (ImageView) findViewById(R.id.backclassdetail2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
            }
        });
        book = (Button) findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            }
        });

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
        for (int i = 0; i < tagpart.length; i++) {
            taglist.add(tagpart[i]);
        }


        if (tagpart.length == 1) {
            buttontags2_searchclass.setVisibility(View.GONE);
            buttontags3_searchclass.setVisibility(View.GONE);
            buttontags1_searchclass.setText(taglist.get(0));
        } else if (tagpart.length == 2) {
            buttontags3_searchclass.setVisibility(View.GONE);
            buttontags1_searchclass.setText(taglist.get(0));
            buttontags2_searchclass.setText(taglist.get(1));
        } else {
            buttontags1_searchclass.setText(taglist.get(0));
            buttontags2_searchclass.setText(taglist.get(1));
            buttontags3_searchclass.setText(taglist.get(2));

        }
//        buttontags1_searchclass.setText(getIntent().getStringExtra("tags"));
        description_searchclass.setText(getIntent().getStringExtra("description"));
        String years = getIntent().getStringExtra("target_year");
        if (years.equals("1")) {
            target_searchclass.setText("First year student");
        } else if (years.equals("2")) {
            target_searchclass.setText("Second year student");
        } else if (years.equals("3")) {
            target_searchclass.setText("Third year student");
        } else if (years.equals("4")) {
            target_searchclass.setText("Fourth year student");
        }
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
        final int semiTransparentGrey = Color.argb(60, 10, 10, 10);
        class_picture.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);

        getClasscount();
        getTeachername();
    }

    @Override
    public void onBackPressed() {

    }

    public void getClasscount() {
        mAPIService.classCount(course_id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                numberofstudent_searchclass.setText(response.body().getCount() + " /");

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    public void getTeachername() {
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
