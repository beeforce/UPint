package com.example.bee.upint2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bee.upint2.Classdetail;
import com.example.bee.upint2.R;
import com.example.bee.upint2.model.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bee on 3/23/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewholder> {

    private List<Course> course;
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private Date d;
    private static final String TAG = "RecycleAdapterCourse";
    public static String scheduletime, scheduledate;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
    }

    public MyRecyclerViewAdapter(List<Course> course, Context context) {
        this.course = course;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewholder(view, context, course);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
        Date today = Calendar.getInstance().getTime();
        try {
            d = input.parse(course.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduledate = output.format(d);
        //get remain date
        long date_remain = d.getTime() - today.getTime();
        long seconds = date_remain / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        Log.w(TAG, "date" + days);
        if (days == 0 | !d.before(today)) {
            holder.Name.setText(course.get(position).getCourse_name());
            holder.price.setText(course.get(position).getPrice_per_student() + "B");
            holder.numberofstudent.setText("0/" + course.get(position).getTotal_student());

            ArrayList<String> taglist = new ArrayList<>();

            String tag = course.get(position).getTags().toString();
            String[] tagpart = tag.split(",");
            for (int i=0; i < tagpart.length;i++){
                taglist.add(tagpart[i]);
            }


            if (tagpart.length == 1){
                holder.tag2.setVisibility(View.GONE);
                holder.tag1.setText(taglist.get(0));
            }
            else if (tagpart.length == 2){
                holder.tag3.setVisibility(View.GONE);
                holder.tag1.setText(taglist.get(0));
                holder.tag2.setText(taglist.get(1));
            }else {
                holder.tag1.setText(taglist.get(0));
                holder.tag2.setText(taglist.get(1));
                holder.tag3.setText(taglist.get(2));

            }


            holder.course_numberstudent.setText("" + course.get(position).getTotal_student());
            //string part url
            String string = course.get(position).getCourse_image_path();
            String[] parts = string.split("/");
            String part1 = parts[0];
            String part4 = parts[3];
            String part5 = parts[4];
            String part6 = parts[5];
            String part7 = parts[6];
            String part8 = parts[7];
            String part9 = parts[8];
            String url_image = part1 + "//192.168.1.13/" + part4 + "/" + part5 + "/" + part6 + "/" + part7 + "/" + part8 + "/" + part9;
            Glide.with(context)
                    .load(url_image)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
                            return false;
                        }
                    })
                    .override(600, 600)
                    .centerCrop()
                    .into(holder.img);

        } else {
            holder.classinfolayout.setVisibility(View.GONE);
        }

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return course.size();
    }

    // stores and recycles views as they are scrolled off screen
    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView Name, price, numberofstudent, tag1, tag2, tag3, course_numberstudent;
        private RelativeLayout classinfolayout;
        private Context ctx;
        private List<Course> course = new ArrayList<Course>();

        public MyViewholder(View itemView, Context ctx, List<Course> course) {
            super(itemView);
            img = itemView.findViewById(R.id.course_photo);
            Name = itemView.findViewById(R.id.course_name);
            price = itemView.findViewById(R.id.price_appfeed);
            tag1 = itemView.findViewById(R.id.buttontags1_keyword);
            tag2 = itemView.findViewById(R.id.buttontags2_keyword);
            tag3 = itemView.findViewById(R.id.buttontags3_keyword);
            numberofstudent = itemView.findViewById(R.id.numberofstudent3);
            course_numberstudent = itemView.findViewById(R.id.totalofstudent3);
            classinfolayout = itemView.findViewById(R.id.classinfolayout3);
            this.course = course;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Course course = this.course.get(position);
            Intent i = new Intent(v.getContext(), Classdetail.class);
            i.putExtra("course_id", course.getId().toString());
            i.putExtra("scheduletime", scheduletime.toString());
            i.putExtra("course_name", course.getCourse_name().toString());
            i.putExtra("level", course.getLevel_of_difficult());
            i.putExtra("description", course.getDescription());
            i.putExtra("cost", course.getPrice_per_student());
            i.putExtra("scheduledate", scheduledate.toString());
            i.putExtra("place", course.getPlace());
            i.putExtra("term", course.getTerms());
            i.putExtra("totalstudent", course.getTotal_student().toString());
            v.getContext().startActivity(i);
        }
    }
}
