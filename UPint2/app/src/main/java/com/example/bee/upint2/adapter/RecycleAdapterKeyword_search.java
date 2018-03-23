package com.example.bee.upint2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bee.upint2.Classdetail_searchclass;
import com.example.bee.upint2.R;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 3/16/2018.
 */

public class RecycleAdapterKeyword_search extends RecyclerView.Adapter<RecycleAdapterKeyword_search.MyViewholder> {

    private List<Course> courselist;
    private ArrayList<Course> courseArrayList = new ArrayList<Course>();
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private ApiService mAPIService;


    public RecycleAdapterKeyword_search(Context context, RecyclerViewClickListener itemListener){
        this.context = context;
        this.itemListener = itemListener;

    }

    public RecycleAdapterKeyword_search(List<Course> course, Context context){
        this.courselist = course;
        this.courseArrayList.addAll(courselist);
        this.context = context;

    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout3, parent, false);
        return new RecycleAdapterKeyword_search.MyViewholder(view, context, courselist);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {

        mAPIService = ApiUtils.getAPIService();

        mAPIService.userDetailswithId(courselist.get(position).getUser_id().toString()).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                holder.teacher_name.setText(response.body().getFirst_name());

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });

        mAPIService.classCount(courselist.get(position).getId().toString()).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                holder.numberofstudent.setText(response.body().getCount()+" /");

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });


//        holder.getClasscount(courselist.get(position).getUser_id());
//        holder.getTeachername(courselist.get(position).getId().toString());

//        holder.numberofstudent.setText(courselist.get(position).getUser_id());
//        holder.teacher_name.setText(courselist.get(position).getId().toString());

        ArrayList<String> taglist = new ArrayList<>();

        String tag = courselist.get(position).getTags().toString();
        String[] tagpart = tag.split(",");
        for (int i=0; i < tagpart.length;i++){
            taglist.add(tagpart[i]);
        }


        if (tagpart.length == 1){
            holder.buttontags2_keyword.setVisibility(View.GONE);
            holder.buttontags1_keyword.setText(taglist.get(0));
        }
        else if (tagpart.length == 2){
            holder.buttontags3_keyword.setVisibility(View.GONE);
            holder.buttontags1_keyword.setText(taglist.get(0));
            holder.buttontags2_keyword.setText(taglist.get(1));
        }else {
            holder.buttontags1_keyword.setText(taglist.get(0));
            holder.buttontags2_keyword.setText(taglist.get(1));
            holder.buttontags3_keyword.setText(taglist.get(2));

        }





        holder.name.setText(courselist.get(position).getCourse_name());
        holder.price.setText(courselist.get(position).getPrice_per_student() + "B");
        holder.totalofstudent.setText(courselist.get(position).getTotal_student().toString());

        String string = courselist.get(position).getCourse_image_path();
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

    }

    @Override
    public int getItemCount() {
        return courselist.size();
    }


    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context ctx;
        private List<Course> course = new ArrayList<Course>();
        private ImageView img;
        private TextView name, price, numberofstudent, totalofstudent, teacher_name;
        private Button buttontags1_keyword, buttontags2_keyword, buttontags3_keyword;
        private Date d;

        public MyViewholder(View itemView, Context ctx, List<Course> course) {
            super(itemView);
            this.course = course;
            this.ctx = ctx;

            img = itemView.findViewById(R.id.course_photo);
            name = itemView.findViewById(R.id.course_name);
            price = itemView.findViewById(R.id.price3);
            numberofstudent = itemView.findViewById(R.id.numberofstudent3);
            totalofstudent = itemView.findViewById(R.id.totalofstudent3);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            buttontags1_keyword =itemView.findViewById(R.id.buttontags1_keyword);
            buttontags2_keyword = itemView.findViewById(R.id.buttontags2_keyword);
            buttontags3_keyword = itemView.findViewById(R.id.buttontags3_keyword);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String timestartst = course.get(getAdapterPosition()).getStart_time();
            String[] timestartpart = timestartst.split(":");
            String timestart1 = timestartpart[0];
            String timestart2 = timestartpart[1];
            String timestart3 = timestartpart[2];
            String timestart = timestart1 + "." + timestart2;
            //string part timefinish
            String timefinishst = course.get(getAdapterPosition()).getFinish_time();
            String[] timefinishpart = timefinishst.split(":");
            String timefinish1 = timefinishpart[0];
            String timefinish2 = timefinishpart[1];
            String timefinish3 = timefinishpart[2];
            String timefinish = timefinish1 + "." + timefinish2;

            int hour = Integer.parseInt(timestart1) % 12;
            int hourfinish = (Integer.parseInt(timefinish1)) % 12;
            int finishhour = Integer.parseInt(timefinish1);
            int finishminute = Integer.parseInt(timefinish2);
            int starthour = Integer.parseInt(timestart1);
            int startminute = Integer.parseInt(timestart2);
            String finishtime = String.format("%02d.%02d %s", hourfinish == 0 ? 12 : hourfinish,
                    finishminute, finishhour < 12 ? "am" : "pm");
            String starttime = String.format("%02d.%02d %s", hour == 0 ? 12 : hour,
                    startminute, starthour < 12 ? "am" : "pm");
            String scheduletime = starttime + " - " + finishtime;

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
            Date today = Calendar.getInstance().getTime();
            try {
                d = input.parse(course.get(getAdapterPosition()).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String scheduledate = output.format(d);

            Intent i = new Intent(v.getContext(), Classdetail_searchclass.class);
            i.putExtra("course_id", course.get(getAdapterPosition()).getId().toString());
            i.putExtra("user_id", course.get(getAdapterPosition()).getUser_id());
            i.putExtra("tags", course.get(getAdapterPosition()).getTags());
            i.putExtra("target_year", course.get(getAdapterPosition()).getTarget_years());
            i.putExtra("scheduletime", scheduletime.toString());
            i.putExtra("course_name", course.get(getAdapterPosition()).getCourse_name().toString());
            i.putExtra("level", course.get(getAdapterPosition()).getLevel_of_difficult());
            i.putExtra("description", course.get(getAdapterPosition()).getDescription());
            i.putExtra("cost", course.get(getAdapterPosition()).getPrice_per_student());
            i.putExtra("scheduledate", scheduledate.toString());
            i.putExtra("place", course.get(getAdapterPosition()).getPlace());
            i.putExtra("term", course.get(getAdapterPosition()).getTerms());
            i.putExtra("totalstudent", course.get(getAdapterPosition()).getTotal_student().toString());
            i.putExtra("image_path", course.get(getAdapterPosition()).getCourse_image_path());
            v.getContext().startActivity(i);
        }

    }
}
