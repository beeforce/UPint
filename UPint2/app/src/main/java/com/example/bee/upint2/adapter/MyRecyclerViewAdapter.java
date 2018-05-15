package com.example.bee.upint2.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bee on 3/23/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewholder> {

    private List<String> cafe_name, CafeImage_path;
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private static final String TAG = "RecycleAdapterCourse";

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
    }

    public MyRecyclerViewAdapter(List<String> name, List<String> image_path, Context context) {
        this.cafe_name = name;
        this.CafeImage_path = image_path;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewholder(view, context, cafe_name,CafeImage_path);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        holder.getView().setAnimation(AnimationUtils.loadAnimation(holder.getcontext(),R.anim.zoom_in));

            holder.Name.setText(cafe_name.get(position));
            //string part url
            String string = CafeImage_path.get(position);
            String[] parts = string.split("/");
            String part1 = parts[0];
            String part4 = parts[3];
            String part5 = parts[4];
            String part6 = parts[5];
            String part7 = parts[6];
            String part8 = parts[7];
            String part9 = parts[8];
            String url_image = part1 + "//192.168.31.164/" + part4 + "/" + part5 + "/" + part6 + "/" + part7 + "/" + part8 + "/" + part9;
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

        final int semiTransparentGrey = Color.argb(100, 10, 10, 10);
        holder.img.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return cafe_name.size();
    }

    // stores and recycles views as they are scrolled off screen
    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private View view;
        private TextView Name;
        private Context ctx;


        public MyViewholder(View itemView, Context ctx, List<String> name, List<String> image_path) {
            super(itemView);
            img = itemView.findViewById(R.id.course_photo);
            Name = itemView.findViewById(R.id.course_name);
            this.ctx = ctx;
            this.view = itemView;
            itemView.setOnClickListener(this);

        }
        public View getView() {
            return this.view;
        }
        public Context getcontext(){
            return this.ctx;
        }

        @Override
        public void onClick(View v) {
            v.setAnimation(AnimationUtils.loadAnimation(getcontext(),R.anim.zoom_in));
//            int position = getAdapterPosition();
//            Course course = this.course.get(position);
        }
    }
}
