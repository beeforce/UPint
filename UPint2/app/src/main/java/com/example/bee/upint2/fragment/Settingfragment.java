package com.example.bee.upint2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.R;

/**
 * Created by Bee on 1/30/2018.
 */

public class Settingfragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String truckDriverId;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_upcoming, container, false);

        initInstances(rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initInstances(View rootView) {

        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName);
        tvProjectName.setText(Html.fromHtml("<b>Setting</b>"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


    }
}