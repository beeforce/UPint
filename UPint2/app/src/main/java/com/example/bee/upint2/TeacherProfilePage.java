package com.example.bee.upint2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TeacherProfilePage extends AppCompatActivity {

    private ImageView profile_image, back;
    private TextView user_name, user_university, university_graduated, major_graduated,telephone, location, introduce;
    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_page);

        //Imageview
        profile_image = findViewById(R.id.profile_image);
        back = findViewById(R.id.back);
        //Textview
        user_name = findViewById(R.id.user_name);
        user_university = findViewById(R.id.user_university);
        university_graduated = findViewById(R.id.university_graduated);
        major_graduated = findViewById(R.id.major_graduated);
        telephone = findViewById(R.id.telephone);
        location = findViewById(R.id.location);
        introduce = findViewById(R.id.introduce);

        image_path = getIntent().getStringExtra("image");
        user_name.setText(getIntent().getStringExtra("teacher_name"));
        user_university.setText(getIntent().getStringExtra(  "University_canteach"));
        university_graduated.setText(getIntent().getStringExtra(  "university_graduated"));
        major_graduated.setText(getIntent().getStringExtra(  "major_graduated"));
        telephone.setText(getIntent().getStringExtra(  "telephone"));
        location.setText(getIntent().getStringExtra(  "location"));
        introduce.setText(getIntent().getStringExtra(  "introduce"));

        String[] parts = image_path.split("/");
        String part1 = parts[0];
        String part4 = parts[3];
        String part5 = parts[4];
        String part6 = parts[5];
        String part7 = parts[6];
        String part8 = parts[7];
        String part9 = parts[8];
        String url_image = part1 + "//192.168.31.164/" + part4 + "/" + part5 + "/" + part6 + "/" + part7 + "/" + part8 + "/" + part9;
        Glide.with(this)
                .load(url_image)
                .override(600, 600)
                .centerCrop()
                .into(profile_image);

        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
