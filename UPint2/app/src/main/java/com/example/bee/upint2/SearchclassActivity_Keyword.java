package com.example.bee.upint2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bee.upint2.adapter.ListViewAdapter;
import com.example.bee.upint2.adapter.RecycleAdapterKeyword_search;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchclassActivity_Keyword extends AppCompatActivity implements RecyclerViewClickListener {

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
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button see50class;
    private RelativeLayout card_view_layout, searchclass_keyword_layout;
    private ImageView arrow_left_img;
    private EditText searchclasskeywordet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchclass__keyword);

        recyclerView = findViewById(R.id.recyclerView_keyword);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        buttontags1_searchclasskeyword = findViewById(R.id.buttontags1_searchclasskeyword);
        buttontags2_searchclasskeyword = findViewById(R.id.buttontags2_searchclasskeyword);
        buttontags3_searchclasskeyword = findViewById(R.id.buttontags3_searchclasskeyword);
        buttontags4_searchclasskeyword = findViewById(R.id.buttontags4_searchclasskeyword);
        buttontags5_searchclasskeyword = findViewById(R.id.buttontags5_searchclasskeyword);
        buttontags6_searchclasskeyword = findViewById(R.id.buttontags6_searchclasskeyword);
        see50class = findViewById(R.id.see50class);
        card_view_layout = findViewById(R.id.card_view_layout);
        searchclass_keyword_layout = findViewById(R.id.searchclass_keyword_layout);
        arrow_left_img = findViewById(R.id.arrow_left_img);
        searchclasskeywordet = findViewById(R.id.searchclasskeywordet);

        getClassdetail();

        arrow_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_view_layout.setVisibility(View.GONE);
                searchclass_keyword_layout.setVisibility(View.VISIBLE);
                finish();
                startActivity(getIntent());
            }
        });

        see50class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccess(course);
                card_view_layout.setVisibility(View.VISIBLE);
                searchclass_keyword_layout.setVisibility(View.GONE);


            }
        });


        buttontags1_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags1_searchclasskeyword.isSelected()){
                    buttontags1_searchclasskeyword.setSelected(true);
                    keywordbt1 ="#Writing";
                    myFilter(keywordbt1);
                }else {
                    buttontags1_searchclasskeyword.setSelected(false);
                    buttontags1_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt1);
                }
            }
        });

        buttontags2_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags2_searchclasskeyword.isSelected()){
                    buttontags2_searchclasskeyword.setSelected(true);
                    keywordbt2 ="#Communication";
                    myFilter(keywordbt2);
                }else {
                    buttontags2_searchclasskeyword.setSelected(false);
                    buttontags2_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt2);
                }

            }
        });

        buttontags3_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags3_searchclasskeyword.isSelected()){
                    buttontags3_searchclasskeyword.setSelected(true);
                    keywordbt3 ="#Interpretive Practices";
                    myFilter(keywordbt3);
                }else {
                    buttontags3_searchclasskeyword.setSelected(false);
                    buttontags3_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt3);
                }

            }
        });

        buttontags4_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags4_searchclasskeyword.isSelected()){
                    buttontags4_searchclasskeyword.setSelected(true);
                    keywordbt4 ="#Professional Seminar";
                    myFilter(keywordbt4);
                }else {
                    buttontags4_searchclasskeyword.setSelected(false);
                    buttontags4_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt4);
                }

            }
        });

        buttontags5_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags5_searchclasskeyword.isSelected()){
                    buttontags5_searchclasskeyword.setSelected(true);
                    keywordbt5 ="#Tutoring";
                    myFilter(keywordbt5);
                }else {
                    buttontags5_searchclasskeyword.setSelected(false);
                    buttontags5_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt5);
                }

            }
        });

        buttontags6_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags6_searchclasskeyword.isSelected()){
                    buttontags6_searchclasskeyword.setSelected(true);
                    keywordbt6 ="#Argument";
                    myFilter(keywordbt6);
                }else {
                    buttontags6_searchclasskeyword.setSelected(false);
                    buttontags6_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt6);
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


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courselist) {
        courselist.clear();
        courselist.addAll(courseArrayList);
        adapterKeyword_search = new RecycleAdapterKeyword_search(courselist,getApplicationContext());
        recyclerView.setAdapter(adapterKeyword_search);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapterKeyword_search = new RecycleAdapterKeyword_search(getApplicationContext(), this);
    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
            for (Course each : course){
                if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                    courseArrayList.remove(each);
            }
        }
        for (Course each : course){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.add(each);
            }
        }

    }

    public void myFilterdelete(String name){

        name = name.toLowerCase(Locale.getDefault());
        for (Course each : course){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.remove(each);
            }
        }

    }
}
