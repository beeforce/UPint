package com.example.bee.upint2.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class UpcomingStudentFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {


    private static final String TAG = "UpcomingStudentFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course_user> courseEnroll;
    private RecycleAdapterCourse adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private Animator spruceAnimator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_upcoming_student, container, false);

        initInstances(rootView);
//        Userdetail();
        return rootView;
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
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
    }

    private void initInstances(View rootView) {


        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);


        spruceAnimator = new Spruce.SpruceBuilder(swipeRefreshLayout)
                .sortWith(new LinearSort(/*interObjectDelay=*/100L, /*reversed=*/false, LinearSort.Direction.TOP_TO_BOTTOM))
                .animateWith(DefaultAnimations.growAnimator(swipeRefreshLayout, /*duration=*/800))
                .start();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


        getClassdetail();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapter = new RecycleAdapterCourse(getActivity(), this);

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

        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        String userId = String.valueOf(user_id);
        // sort by id
        for (Course each : courseList) {
            if (!each.getUser_id().equals(userId)){
                filteredJob.add(each);
            }
        }
        courseList.removeAll(filteredJob);
        adapter = new RecycleAdapterCourse(courseList, getActivity());
        recyclerView.setAdapter(adapter);
    }



}