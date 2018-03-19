package com.example.bee.upint2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bee.upint2.adapter.ListViewAdapter;
import com.example.bee.upint2.adapter.RecycleAdapterKeyword_search;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchclassActivity_Keyword extends AppCompatActivity {

    private Button buttontags1_searchclasskeyword, buttontags2_searchclasskeyword,
            buttontags3_searchclasskeyword, buttontags4_searchclasskeyword,
            buttontags5_searchclasskeyword, buttontags6_searchclasskeyword;
    private static String TAG = "SearchclassActivity_Keyword";
    private String keywordbt1, keywordbt2, keywordbt3, keywordbt4, keywordbt5
            ,keywordbt6;
    private List<Course> course;
    private ApiService mAPIService;
    private ArrayList<Course> courseArrayList = new ArrayList<Course>();
    private RecycleAdapterKeyword_search adapterKeyword_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchclass__keyword);

        getClassdetail();

        buttontags1_searchclasskeyword = (Button) findViewById(R.id.buttontags1_searchclasskeyword);
        buttontags2_searchclasskeyword = (Button) findViewById(R.id.buttontags2_searchclasskeyword);
        buttontags3_searchclasskeyword = (Button) findViewById(R.id.buttontags3_searchclasskeyword);
        buttontags4_searchclasskeyword = (Button) findViewById(R.id.buttontags4_searchclasskeyword);
        buttontags5_searchclasskeyword = (Button) findViewById(R.id.buttontags5_searchclasskeyword);
        buttontags6_searchclasskeyword = (Button) findViewById(R.id.buttontags6_searchclasskeyword);


        buttontags1_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags1_searchclasskeyword.isSelected()){
                    buttontags1_searchclasskeyword.setSelected(true);
                    keywordbt1 ="#Writing";
                    Toast.makeText(getApplicationContext(),keywordbt1,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags1_searchclasskeyword.setSelected(false);
                    buttontags1_searchclasskeyword.setPressed(false);
                    keywordbt1 ="";
                }
            }
        });

        buttontags2_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags2_searchclasskeyword.isSelected()){
                    buttontags2_searchclasskeyword.setSelected(true);
                    keywordbt2 ="#Communication";
                    Toast.makeText(getApplicationContext(),keywordbt2,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags2_searchclasskeyword.setSelected(false);
                    buttontags2_searchclasskeyword.setPressed(false);
                    keywordbt2 ="";
                }

            }
        });

        buttontags3_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags3_searchclasskeyword.isSelected()){
                    buttontags3_searchclasskeyword.setSelected(true);
                    keywordbt3 ="#Interpretive Pratice";
                    Toast.makeText(getApplicationContext(),keywordbt3,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags3_searchclasskeyword.setSelected(false);
                    buttontags3_searchclasskeyword.setPressed(false);
                    keywordbt3 ="";
                }

            }
        });

        buttontags4_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags4_searchclasskeyword.isSelected()){
                    buttontags4_searchclasskeyword.setSelected(true);
                    keywordbt4 ="#Professional Seminar";
                    Toast.makeText(getApplicationContext(),keywordbt4,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags4_searchclasskeyword.setSelected(false);
                    buttontags4_searchclasskeyword.setPressed(false);
                    keywordbt4 ="";
                }

            }
        });

        buttontags5_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags5_searchclasskeyword.isSelected()){
                    buttontags5_searchclasskeyword.setSelected(true);
                    keywordbt5 ="#Tutoring";
                    Toast.makeText(getApplicationContext(),keywordbt5,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags5_searchclasskeyword.setSelected(false);
                    buttontags5_searchclasskeyword.setPressed(false);
                    keywordbt5 ="";
                }

            }
        });

        buttontags6_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags6_searchclasskeyword.isSelected()){
                    buttontags6_searchclasskeyword.setSelected(true);
                    keywordbt6 ="#Argument";
                    Toast.makeText(getApplicationContext(),keywordbt6,
                            Toast.LENGTH_SHORT).show();
                }else {
                    buttontags6_searchclasskeyword.setSelected(false);
                    buttontags6_searchclasskeyword.setPressed(false);
                    keywordbt6 ="";
                }

            }
        });
    }
    public void getClassdetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCoursedetail().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                course = response.body();
                onSuccess(course);


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courselist) {
        courseArrayList.addAll(courselist);
        adapterKeyword_search = new RecycleAdapterKeyword_search(courselist,getApplicationContext());
    }

}
