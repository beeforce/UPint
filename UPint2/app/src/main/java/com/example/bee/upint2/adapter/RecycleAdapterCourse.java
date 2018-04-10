package com.example.bee.upint2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bee on 2/21/2018.
 */

public class RecycleAdapterCourse extends RecyclerView.Adapter<RecycleAdapterCourse.MyViewholder> {

    private List<Course> course;
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private Date d;
    private static final String TAG = "RecycleAdapterCourse";
    public static String scheduletime, scheduledate;

    public RecycleAdapterCourse(Context context, RecyclerViewClickListener itemListener) {

        this.context = context;
        this.itemListener = itemListener;
    }

    public RecycleAdapterCourse(List<Course> course, Context context) {

        this.course = course;
        this.context = context;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout, parent, false);
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
        holder.date.setText("" + output.format(d));
        scheduledate = output.format(d);
        String day = (String) DateFormat.format("dd", d);
        String to_day = (String) DateFormat.format("dd", today);
        Log.w(TAG, "date format " + day);
        Log.w(TAG, "date format today " + to_day);
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
            //string part timestart
            String timestartst = course.get(position).getStart_time();
            String[] timestartpart = timestartst.split(":");
            String timestart1 = timestartpart[0];
            String timestart2 = timestartpart[1];
            String timestart3 = timestartpart[2];
            String timestart = timestart1 + "." + timestart2;
            //string part timefinish
            String timefinishst = course.get(position).getFinish_time();
            String[] timefinishpart = timefinishst.split(":");
            String timefinish1 = timefinishpart[0];
            String timefinish2 = timefinishpart[1];
            String timefinish3 = timefinishpart[2];
            String timefinish = timefinish1 + "." + timefinish2;
            holder.time.setText(timestart + " - " + timefinish);

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
            scheduletime = starttime + " - " + finishtime;

            if (days > 0) {
                int a = 1;
                int b = (int) days;
                holder.dateremain.setText("D-" + (a + b));
            } else if (days == 0 & hour > 0 && day != to_day) {
                holder.dateremain.setText("D-1");
            } else if (day.equals(to_day)) {
                holder.dateremain.setText("Today");
            }

            ArrayList<String> taglist = new ArrayList<>();

            String tag = course.get(position).getTags().toString();
            String[] tagpart = tag.split(",");
            for (int i = 0; i < tagpart.length; i++) {
                taglist.add(tagpart[i]);
            }


            if (tagpart.length == 1) {
                holder.tag2.setVisibility(View.GONE);
                holder.tag1.setText(taglist.get(0));
            } else if (tagpart.length == 2) {
                holder.tag3.setVisibility(View.GONE);
                holder.tag1.setText(taglist.get(0));
                holder.tag2.setText(taglist.get(1));
            } else {
                holder.tag1.setText(taglist.get(0));
                holder.tag2.setText(taglist.get(1));
                holder.tag3.setText(taglist.get(2));

            }


            holder.course_place.setText(course.get(position).getPlace());
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
            final int semiTransparentGrey = Color.argb(60, 10, 10, 10);
            holder.img.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);

        } else {
            holder.classinfolayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return course.size();
    }


    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView Name, price, time, numberofstudent, date, tag1, tag2, tag3, dateremain, course_place, course_numberstudent;
        private RelativeLayout classinfolayout;
        private Context ctx;
        private List<Course> course = new ArrayList<Course>();

        public MyViewholder(View itemView, Context ctx, List<Course> course) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            Name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.course_date);
            time = itemView.findViewById(R.id.course_time);
            tag1 = itemView.findViewById(R.id.buttontags1);
            tag2 = itemView.findViewById(R.id.buttontags2);
            tag3 = itemView.findViewById(R.id.buttontags3);
            numberofstudent = itemView.findViewById(R.id.numberofstudent);
            dateremain = itemView.findViewById(R.id.dateremain);
            course_place = itemView.findViewById(R.id.course_place);
            course_numberstudent = itemView.findViewById(R.id.course_numberstudent);
            classinfolayout = itemView.findViewById(R.id.classinfolayout);
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
