package com.example.bee.upint2.fragment;

import android.content.Context;
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
import com.example.bee.upint2.Main2Activity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Listenfragment  extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Listenfragment";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context mContext;
    private ApiService mAPIService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_litening, container, false);

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName2);
        tvProjectName.setText(Html.fromHtml("<b>Your Listening</b>"));
        return rootView;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

}
