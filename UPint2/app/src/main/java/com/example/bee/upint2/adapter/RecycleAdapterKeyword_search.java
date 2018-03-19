package com.example.bee.upint2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.bee.upint2.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bee on 3/16/2018.
 */

public class RecycleAdapterKeyword_search extends RecyclerView.Adapter<RecycleAdapterKeyword_search.MyViewholder> {

    private List<Course> courselist;
    private ArrayList<Course> courseArrayList = new ArrayList<Course>();
    private Context context;
    private static RecyclerViewClickListener itemListener;

    public RecycleAdapterKeyword_search(Context context, RecyclerViewClickListener itemListener){
        this.context = context;
        this.itemListener = itemListener;

    }

    public RecycleAdapterKeyword_search(List<Course> course, Context context){
        this.courselist = course;
        this.context = context;

    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return courselist.size();
    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
        courselist.clear();
        if (name.length() == 0){
            courselist.addAll(courseArrayList);
        }else{
            for (Course each : courseArrayList){
                if (each.getCourse_name().toLowerCase(Locale.getDefault()).contains(name)){
                    courselist.add(each);
                }
            }
        }

    }

    public static class MyViewholder extends RecyclerView.ViewHolder{

        public MyViewholder(View itemView) {
            super(itemView);
        }
    }
}
