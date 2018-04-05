package com.example.bee.upint2.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.bee.upint2.AppfeedActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterCourse;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Course_user;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.LinearSort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpcomingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private static final String TAG = "Upcomingfragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course_user> courseEnroll;
    private RecycleAdapterCourse adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private Animator spruceAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);


        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.recyclerView);


        spruceAnimator = new Spruce.SpruceBuilder(swipeRefreshLayout)
                .sortWith(new LinearSort(/*interObjectDelay=*/100L, /*reversed=*/false, LinearSort.Direction.TOP_TO_BOTTOM))
                .animateWith(DefaultAnimations.growAnimator(swipeRefreshLayout, /*duration=*/800))
                .start();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpcomingActivity.this,AppfeedActivity.class);
                startActivity(i);
            }
        });


        getClassdetail();


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRefresh() {
        recyclerView.removeAllViewsInLayout();
        course.clear();
        getClassdetail();
        swipeRefreshLayout.setRefreshing(false);
        spruceAnimator.start();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapter = new RecycleAdapterCourse(getApplicationContext(), this);

    }

    public void getClassdetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCoursedetail().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                course = response.body();
                searchClass();


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courseList) {
        List<Course> filteredJob = new ArrayList<Course>();

        final Date[] d1 = new Date[1];
        final Date[] d2 = new Date[1];

        // sort by date
        Collections.sort(courseList, new Comparator<Course>() {

            @Override
            public int compare(Course o1, Course o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    d1[0] = input.parse(o1.getDate());
                    d2[0] = input.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1[0].compareTo(d2[0]);
            }

        });

        // sort by id
        for (Course each : courseList) {
            for (Course_user each1 : courseEnroll) {
                if (each.getId().equals(each1.getCourse_id())) {
                    filteredJob.add(each);
                    Log.w(TAG, "find course id" + each.getId() + "  " + each1.getCourse_id());
                }
            }
        }
        adapter = new RecycleAdapterCourse(filteredJob, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void searchClass() {

        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService = ApiUtils.getAPIService();
        mAPIService.searchclassEnroll(user_id).enqueue(new Callback<List<Course_user>>() {
            @Override
            public void onResponse(Call<List<Course_user>> call, Response<List<Course_user>> response) {
                if (response.isSuccessful()) {
                    courseEnroll = response.body();
                    onSuccess(course);
                } else
                    Log.w(TAG, "Search class onResponse:fail");
            }

            @Override
            public void onFailure(Call<List<Course_user>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
