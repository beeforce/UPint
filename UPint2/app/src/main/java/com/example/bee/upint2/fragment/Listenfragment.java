package com.example.bee.upint2.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bee.upint2.MakeclassActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterListening;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.UserProfile;
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

/**
 * Created by Bee on 1/30/2018.
 */

public class Listenfragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private static final String TAG = "Listenfragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course> courselistenList = new ArrayList<>();
    private Course courselisten;
    //    private List<Integer> courseid = new ArrayList<Integer>();
    private RecycleAdapterListening adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private Animator spruceAnimator;
    private String class_count;
    private Button makeclass_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_litening, container, false);
        mAPIService = ApiUtils.getAPIService();
        if (course != null) {
            course.clear();
            courselistenList.clear();
        }

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {


        makeclass_button = rootView.findViewById(R.id.makeclassbt);
        makeclass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeclassActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout = rootView.findViewById(R.id.refresh2);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView2);

        spruceAnimator = new Spruce.SpruceBuilder(swipeRefreshLayout)
                .sortWith(new LinearSort(/*interObjectDelay=*/100L, /*reversed=*/false, LinearSort.Direction.TOP_TO_BOTTOM))
                .animateWith(DefaultAnimations.growAnimator(swipeRefreshLayout, /*duration=*/800))
                .start();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        getClassdetailwithId();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
    }

    @Override
    public void onRefresh() {
        course.clear();
        courselistenList.clear();
        getClassdetailwithId();
        swipeRefreshLayout.setRefreshing(false);
        spruceAnimator.start();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapter = new RecycleAdapterListening(getActivity(), this);
    }

    public void getClassdetailwithId() {
        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService.courseListening(user_id).enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    course = response.body();
                    onSuccess(course);
                    Log.w(TAG, "onResponse: " + course.size());
                } else {

                    Log.w(TAG, "onResponseFail: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void onSuccess(List<Course> courseList) {

        final Date[] d1 = new Date[1];
        final Date[] d2 = new Date[1];

        // sort by date
        Collections.sort(courseList, new Comparator<Course>() {

            @Override
            public int compare(Course o1, Course o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    d1[0] = input.parse(o1.getDate()+" "+o1.getStart_time());
                    d2[0] = input.parse(o2.getDate()+" "+o2.getStart_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1[0].compareTo(d2[0]);
            }

        });

        for (Course each : courseList) {
            Log.w(TAG, "class id" + each.getId());
        }
        if (courseList == null) {
            makeclass_button.setVisibility(View.VISIBLE);
        }

        adapter = new RecycleAdapterListening(courseList, getActivity());
        recyclerView.setAdapter(adapter);

    }

    public void Classcount(String course_id) {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.classCount(course_id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.w(TAG, "class count" + response.body().getCount().toString());

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }
}
