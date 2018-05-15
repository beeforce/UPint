package com.example.bee.upint2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
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
import com.example.bee.upint2.TeacherProfilePage;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Teacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapterTeacherStar extends RecyclerView.Adapter<MyRecyclerViewAdapterTeacherStar.MyViewholder> {

    private List<Teacher> TeacherList;
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private Date d;
    private static final String TAG = "RecycleAdapterCourse";
    public static String scheduletime, scheduledate;

    // data is passed into the constructor
    public MyRecyclerViewAdapterTeacherStar(Context context, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
    }

    public MyRecyclerViewAdapterTeacherStar(List<Teacher> course, Context context) {
        this.TeacherList = course;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_teacher_star, parent, false);
        return new MyViewholder(view, context, TeacherList);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        holder.getView().setAnimation(AnimationUtils.loadAnimation(holder.getcontext(), R.anim.zoom_in));
        holder.Name.setText(TeacherList.get(position).getFirst_name());
        holder.Surname.setText(TeacherList.get(position).getLast_name());
        holder.University.setText(TeacherList.get(position).getUniversity_can_teach());

        //string part url
        String string = TeacherList.get(position).getImage();
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
        final int semiTransparentGrey = Color.argb(80, 10, 10, 10);
        holder.img.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return TeacherList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private View view;
        private TextView Name, Surname, University;
        private Context ctx;
        private List<Teacher> TeacherList;

        public MyViewholder(View itemView, Context ctx, List<Teacher> course) {
            super(itemView);
            img = itemView.findViewById(R.id.course_photo);
            Name = itemView.findViewById(R.id.teacher_name);
            Surname = itemView.findViewById(R.id.teacher_surname);
            University = itemView.findViewById(R.id.University);
            this.TeacherList = course;
            this.ctx = ctx;
            this.view = itemView;
            itemView.setOnClickListener(this);

        }

        public View getView() {
            return this.view;
        }

        public Context getcontext() {
            return this.ctx;
        }

        @Override
        public void onClick(View v) {
            v.setAnimation(AnimationUtils.loadAnimation(getcontext(), R.anim.zoom_in));
            int position = getAdapterPosition();
            Teacher teacher = this.TeacherList.get(position);
            Intent i = new Intent(v.getContext(), TeacherProfilePage.class);
            i.putExtra("teacher_name", teacher.getFirst_name()+" "+teacher.getLast_name());
            i.putExtra("University_canteach", teacher.getUniversity_can_teach());
            i.putExtra("image", teacher.getImage());
            i.putExtra("university_graduated", teacher.getSchool());
            i.putExtra("major_graduated", teacher.getMajor());
            i.putExtra("telephone", teacher.getPhone_number());
            i.putExtra("location", teacher.getLocation_city());
            i.putExtra("introduce", teacher.getIntroduce());
            v.getContext().startActivity(i);
        }
    }
}