package com.example.bee.upint2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bee.upint2.R;
import com.example.bee.upint2.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bee on 3/9/2018.
 */

public class ListViewAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Course> courselist;
    private ArrayList<Course> courseArrayList = new ArrayList<Course>();

    public ListViewAdapter(Context context, List<Course> courselist){
        this.context = context;
        this.courselist = courselist;
        this.courseArrayList.addAll(courselist);
        layoutInflater = LayoutInflater.from(context);

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

    @Override
    public int getCount() {
        return courselist.size();
    }

    @Override
    public Course getItem(int position) {
        return courselist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.model_item,null);
            holder.textView = (TextView) convertView.findViewById(R.id.nameList);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(courselist.get(position).getCourse_name());
        return convertView;
    }

    public class ViewHolder{
        TextView textView;
    }
}
