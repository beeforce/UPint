package com.example.bee.upint2.adapter;

import android.content.Context;
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
import com.example.bee.upint2.R;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
import java.util.Collection;
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
            Toast.makeText(ctx,"position"+course.get(getAdapterPosition()).getId(),Toast.LENGTH_SHORT).show();
        }

    }
}
