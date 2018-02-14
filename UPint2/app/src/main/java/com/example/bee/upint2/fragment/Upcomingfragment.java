package com.example.bee.upint2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.LoginActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Upcomingfragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "Upcomingfragment";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private UserProfile userProfile;
    private TextView tvName, tvTruckId, tvAddress;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_upcoming, container, false);

//        String name =this.getArguments().getString("NAME_KEY").toString();
        initInstances(rootView);
//        Userdetail();
        return rootView;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initInstances(View rootView) {


        tvAddress = rootView.findViewById(R.id.tvAddress);
        tvName = rootView.findViewById(R.id.tvName);
        tvTruckId = rootView.findViewById(R.id.tvTruckId);

        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName);
        tvProjectName.setText(Html.fromHtml("<b>Upcoming</b>"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

    }


    //ยังไม่เสด
//    private void Userdetail() {
//
//        mAPIService = ApiUtils.getAPIService();
//        mAPIService.userDetail("Bee@hotmail.com").enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                if (response.isSuccessful()) {
//                    Log.w(TAG, "onResponse: " + response.body() );
//                    onSuccess(response.body());
//                } else {
//                    Log.w(TAG, "onResponsefail ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//                Log.w(TAG, "onfailure"+t.getMessage());
//            }
//        });
//    }
//
//    private void onSuccess(UserProfile userProfile){
//
//        tvName.setText(userProfile.getFirst_name()+" | "+userProfile.getLast_name());
//        tvTruckId.setText(userProfile.getSchool()+" | "+userProfile.getMajor()+" | "+userProfile.getDate_graduated());
//        tvAddress.setText(userProfile.getImage());
//
//    }

}
