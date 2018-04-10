package com.example.bee.upint2.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterListening;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;


public class Explorefragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleAdapterListening adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;

    @Override
    public void onRefresh() {

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        mAPIService = ApiUtils.getAPIService();
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.refresh2);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView2);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


    }
}
