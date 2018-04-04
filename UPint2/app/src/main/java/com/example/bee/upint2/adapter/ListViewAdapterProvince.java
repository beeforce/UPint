package com.example.bee.upint2.adapter;

import android.content.Context;
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

public class ListViewAdapterProvince extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> provincelist;
    private ArrayList<String> provinceArrayList = new ArrayList<>();

    public ListViewAdapterProvince(Context context, List<String> provincelist){
        this.context = context;
        this.provincelist = provincelist;
        this.provinceArrayList.addAll(provincelist);
        layoutInflater = LayoutInflater.from(context);

    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
        provincelist.clear();
        if (name.length() == 0){
            provincelist.addAll(provinceArrayList);
        }else{
            for (String each : provinceArrayList){
                if (each.toString().toLowerCase(Locale.getDefault()).contains(name)){
                    provincelist.add(each);
                }
            }
        }

    }

    @Override
    public int getCount() {
        return provincelist.size();
    }

    @Override
    public String getItem(int position) {
        return provincelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListViewAdapterProvince.ViewHolder holder;
        if (convertView == null){
            holder = new ListViewAdapterProvince.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.model_item,null);
            holder.textView = (TextView) convertView.findViewById(R.id.nameList);
            convertView.setTag(holder);
        }else {
            holder = (ListViewAdapterProvince.ViewHolder) convertView.getTag();
        }
        holder.textView.setText(provincelist.get(position).toString());
        return convertView;
    }


    public class ViewHolder{
        TextView textView;
    }
}