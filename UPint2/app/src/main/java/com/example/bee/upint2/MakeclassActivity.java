package com.example.bee.upint2;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.timessquare.CalendarPickerView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MakeclassActivity extends AppCompatActivity {

    private EditText scheduleEt;
    private RelativeLayout relay1, relay2, starttimerelay, finishtimerelay, calendarlayout, showtimebooking;
    private Button savetime, save;
    private TimePicker timePickerstart, timePickerfinish;
    private int starthour, startminute, finishhour, finishminute;
    private String Bookingdate;
    private TextView Bookingtime, Bookingdatemakeclass;
    private Bitmap mImageGeneratedDateicon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratorImage;
    private Calendar mCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set language
        String languageToLoad = "en_US"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale.ENGLISH; //set locale language to english
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_makeclass);

        mImageGenerator = new ImageGenerator(this);
        mDisplayGeneratorImage = (ImageView) findViewById(R.id.imageGen);

        mImageGenerator.setIconSize(55,55);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

        mImageGenerator.setDatePosition(46);
        mImageGenerator.setMonthPosition(16);

        mImageGenerator.setDateColor(Color.parseColor("#269900"));
        mImageGenerator.setMonthColor(Color.WHITE);

        mImageGenerator.setStorageToSDCard(true);

        calendarlayout = (RelativeLayout) findViewById(R.id.calendarlayout);
        Bookingdatemakeclass = (TextView) findViewById(R.id.datetimemakeclass);
        Bookingtime = (TextView) findViewById(R.id.bookingTime);
        timePickerstart = (TimePicker) findViewById(R.id.timepickerMakeclass);
        timePickerfinish = (TimePicker) findViewById(R.id.timepickerMakeclass2);
        savetime = (Button) findViewById(R.id.savetime);
        save = (Button) findViewById(R.id.save);
        relay1 = (RelativeLayout) findViewById(R.id.makeclassrelay1);
        relay2 = (RelativeLayout) findViewById(R.id.makeclassrelay2);
        starttimerelay = (RelativeLayout) findViewById(R.id.starttimelayout);
        finishtimerelay = (RelativeLayout) findViewById(R.id.finitimelayout);
        showtimebooking = (RelativeLayout) findViewById(R.id.showtimebooking);
        showtimebooking.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        relay2.setVisibility(View.INVISIBLE);
        scheduleEt = (EditText) findViewById(R.id.Schdule);


        savetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttimerelay.setVisibility(View.INVISIBLE);
                finishtimerelay.setVisibility(View.INVISIBLE);
                savetime.setVisibility(View.INVISIBLE);
                showtimebooking.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                starthour = timePickerstart.getCurrentHour();
                startminute = timePickerstart.getCurrentMinute();
                finishhour = timePickerfinish.getCurrentHour();
                finishminute = timePickerfinish.getCurrentMinute();
                int hour = starthour % 12;
                int hourfinish = finishhour % 12;
                String finishtime = String.format("%02d:%02d %s", hourfinish == 0 ? 12 : hourfinish,
                        finishminute, finishhour < 12 ? "am" : "pm");
                String starttime = String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                        startminute, starthour < 12 ? "am" : "pm");
                Bookingtime.setText(starttime + " - " + finishtime);
                //set date = today
                Date today = new Date();
                Calendar nextYear = Calendar.getInstance();
                nextYear.add(Calendar.YEAR, 1);
                //get time picker
                CalendarPickerView datePicker = findViewById(R.id.calendar);
                datePicker.init(today, nextYear.getTime())
                        .withSelectedDate(today);
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(today);
                //selectedDate for textview
                String selectedDate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + " " + calSelected.get(Calendar.YEAR);
                //Bookingdate for store in database
                Bookingdate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + "/" + calSelected.get(Calendar.YEAR);
                Bookingdatemakeclass.setText(selectedDate);
                calendarlayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 640));

                ///set image
                calSelected.set(today.getYear(),today.getMonth(),today.getDate());

                mImageGeneratedDateicon = mImageGenerator.generateDateImage(calSelected, R.drawable.empty_calendar);
                mDisplayGeneratorImage.setImageBitmap(mImageGeneratedDateicon);
            }
        });
        scheduleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relay1.setVisibility(View.INVISIBLE);
                relay2.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relay1.setVisibility(View.VISIBLE);
                relay2.setVisibility(View.INVISIBLE);
                scheduleEt.setText(Bookingdate);
            }
        });


        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);
//
//                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
//                        + " " + (calSelected.get(Calendar.MONTH) + 1)
//                        + " " + calSelected.get(Calendar.YEAR);

                String selectedDate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + " " + calSelected.get(Calendar.YEAR);
                Bookingdate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + "/" + calSelected.get(Calendar.YEAR);
                Bookingdatemakeclass.setText(selectedDate);

                calSelected.set(date.getYear(),date.getMonth(),date.getDate());

                mImageGeneratedDateicon = mImageGenerator.generateDateImage(calSelected, R.drawable.empty_calendar);
                mDisplayGeneratorImage.setImageBitmap(mImageGeneratedDateicon);

                Toast.makeText(MakeclassActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }
}
