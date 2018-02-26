package com.example.bee.upint2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bee.upint2.fragment.Schedulefragment;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.example.bee.upint2.utils.FileUtils;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.timessquare.CalendarPickerView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeclassActivity extends AppCompatActivity {


    private static final String TAG = "MakeclassActivity";
    private RelativeLayout relay1, relay2, relay3, relay4, starttimerelay, finishtimerelay, calendarlayout, showtimebooking , relayuploadpicture;
    private Button savetime, save, nextbt1, nextbt2, uploadpicturemakeclass;
    private TimePicker timePickerstart, timePickerfinish;
    private int starthour, startminute, finishhour, finishminute;
    private String Bookingdate, bookingtimestart, bookingtimefinish, bookingdate, yearsmakeclass;
    private TextView Bookingtime, Bookingdatemakeclass;
    private Bitmap mImageGeneratedDateicon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratorImage, makeclassuploadpicture;
    private CalendarPickerView datePicker;
    private Calendar calSelected;
    private Date today;
    private ApiService mAPIService;
    private Button confirmsetschedule;
    private SweetAlertDialog pDialog;
    private TextInputLayout PriceperStudentTextlayout, tillClassnameTextlayout,
            tillUniversityTextlayout, tillMajormakeclassTextlayout,
            tilllevelTextlayout, tilltotalnumberstudentTextlayout, tillPlaceTextlayout;
    private EditText scheduleEt, descriptionEt, termmakeclassEt, tagmakeclassEt;
    private Uri resultUri;
    private Vibrator vibrator;
    private Animation anim;

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

        //set api service
        mAPIService = ApiUtils.getAPIService();

        mImageGenerator = new ImageGenerator(this);
        mDisplayGeneratorImage = (ImageView) findViewById(R.id.imageGen);

        mImageGenerator.setIconSize(55, 55);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

        mImageGenerator.setDatePosition(46);
        mImageGenerator.setMonthPosition(16);

        mImageGenerator.setDateColor(Color.parseColor("#269900"));
        mImageGenerator.setMonthColor(Color.WHITE);

//        mImageGenerator.setStorageToSDCard(true);

        final Spinner spinner = (Spinner) findViewById(R.id.yearmakeclass);

        // Initializing a String Array
        String[] plants = new String[]{
                "Year",
                "1",
                "2",
                "3",
                "4"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    yearsmakeclass = selectedItemText.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        selectyearSpinner = (Spinner) findViewById(R.id.yearmakeclass);
//        selectyearSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                yearsmakeclass = selectyearSpinner.getSelectedItem().toString();
//            }
//        });
        //Imageview
        makeclassuploadpicture = (ImageView) findViewById(R.id.makeclassuploadpicture);
        //edittext
        termmakeclassEt = (EditText) findViewById(R.id.termmakeclass);
        descriptionEt = (EditText) findViewById(R.id.description);
        descriptionEt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        descriptionEt.setRawInputType(InputType.TYPE_CLASS_TEXT);
        scheduleEt = (EditText) findViewById(R.id.Schdule);
        tagmakeclassEt = (EditText) findViewById(R.id.tagmakeclass);
        //Textinputlayout
        PriceperStudentTextlayout = (TextInputLayout) findViewById(R.id.PriceperStudentTextlayout);
        tillClassnameTextlayout = (TextInputLayout) findViewById(R.id.ClassnameTextlayout);
        tillUniversityTextlayout = (TextInputLayout) findViewById(R.id.UniversityTextlayout);
        tillMajormakeclassTextlayout = (TextInputLayout) findViewById(R.id.MajormakeclassTextlayout);
        tilllevelTextlayout = (TextInputLayout) findViewById(R.id.levelTextlayout);
        tilltotalnumberstudentTextlayout = (TextInputLayout) findViewById(R.id.totalnumberstudentTextlayout);
        tillPlaceTextlayout = (TextInputLayout) findViewById(R.id.PlaceTextlayout);
        //textview
        Bookingdatemakeclass = (TextView) findViewById(R.id.datetimemakeclass);
        Bookingtime = (TextView) findViewById(R.id.bookingTime);
        //time picker
        timePickerstart = (TimePicker) findViewById(R.id.timepickerMakeclass);
        timePickerfinish = (TimePicker) findViewById(R.id.timepickerMakeclass2);
        //button
        savetime = (Button) findViewById(R.id.savetime);
        save = (Button) findViewById(R.id.save);
        confirmsetschedule = (Button) findViewById(R.id.confirmsetschedule);
        nextbt1 = (Button) findViewById(R.id.nextmakeclass1);
        nextbt2 = (Button) findViewById(R.id.nextmakeclass2);
        uploadpicturemakeclass = (Button) findViewById(R.id.uploadpicturemakeclass);
        //Relativelayout
        calendarlayout = (RelativeLayout) findViewById(R.id.calendarlayout);
        relay1 = (RelativeLayout) findViewById(R.id.makeclassrelay1);
        relay2 = (RelativeLayout) findViewById(R.id.makeclassrelay2);
        relay3 = (RelativeLayout) findViewById(R.id.makeclassrelay3);
        relay4 = (RelativeLayout) findViewById(R.id.makeclassrelay4);
        starttimerelay = (RelativeLayout) findViewById(R.id.starttimelayout);
        finishtimerelay = (RelativeLayout) findViewById(R.id.finitimelayout);
        showtimebooking = (RelativeLayout) findViewById(R.id.showtimebooking);
        relayuploadpicture = (RelativeLayout) findViewById(R.id.relayuploadpicture);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
        anim.setRepeatCount(2);

        //set visible
        showtimebooking.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        relay2.setVisibility(View.INVISIBLE);
        relay3.setVisibility(View.INVISIBLE);
        relay4.setVisibility(View.INVISIBLE);
        makeclassuploadpicture.setVisibility(View.INVISIBLE);

        nextbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkClassname() | !checktargetmajor() | !checktargetUniversity()) {
                    tillClassnameTextlayout.setAnimation(anim);
                    tillUniversityTextlayout.setAnimation(anim);
                    tillMajormakeclassTextlayout.setAnimation(anim);
                    checkClassname();
                    checktargetmajor();
                    checktargetUniversity();
                    vibrator.vibrate(120);
                    return;
                }else {
                    relay1.setVisibility(View.INVISIBLE);
                    relay2.setVisibility(View.VISIBLE);
                }
            }
        });

        nextbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkterm() | !checklevel() |
                        !checktotalnumber() | !checkdescription() |
                        !checktagmakeclass()){
                    termmakeclassEt.setAnimation(anim);
                    tilllevelTextlayout.setAnimation(anim);
                    tilltotalnumberstudentTextlayout.setAnimation(anim);
                    descriptionEt.setAnimation(anim);
                    tagmakeclassEt.setAnimation(anim);
                    checkterm();
                    checklevel();
                    checktotalnumber();
                    checkdescription();
                    checktagmakeclass();
                    vibrator.vibrate(120);
                    return;
                }else {
                    relay2.setVisibility(View.INVISIBLE);
                    relay3.setVisibility(View.VISIBLE);
                }
            }
        });

        uploadpicturemakeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeclassuploadpicture.setVisibility(View.VISIBLE);
                relayuploadpicture.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

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
                //Bookingtime.setText(String.format("%02d:%02d", starthour, startminute) + " - " + String.format("%02d:%02d", finishhour, finishminute));

                //set date = today
                Calendar nextYear = Calendar.getInstance();
                nextYear.add(Calendar.YEAR, 1);
                calSelected = Calendar.getInstance();
                calSelected.setTime(today);
                //get time picker
                //selectedDate for textview
                String selectedDate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + " " + calSelected.get(Calendar.YEAR);
                //Bookingdate for store in database
                Bookingdate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (calSelected.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                        + "/" + calSelected.get(Calendar.YEAR);

                bookingdate = calSelected.get(Calendar.YEAR)+ "-" + (calSelected.get(Calendar.MONTH) + 1) + "-"+ calSelected.get(Calendar.DAY_OF_MONTH);
                Bookingdatemakeclass.setText(selectedDate);
                calendarlayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 640));

                bookingtimestart = String.format("%02d", starthour) + ":" + String.format("%02d", startminute);
                bookingtimefinish = String.format("%02d", finishhour) + ":" + String.format("%02d", finishminute);


                ///set image
                calSelected.set(today.getYear(), today.getMonth(), today.getDate());

                mImageGeneratedDateicon = mImageGenerator.generateDateImage(calSelected, R.drawable.empty_calendar);
                mDisplayGeneratorImage.setImageBitmap(mImageGeneratedDateicon);
            }
        });
        scheduleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relay3.setVisibility(View.INVISIBLE);
                relay4.setVisibility(View.VISIBLE);
                starttimerelay.setVisibility(View.VISIBLE);
                finishtimerelay.setVisibility(View.VISIBLE);
                savetime.setVisibility(View.VISIBLE);
                showtimebooking.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relay3.setVisibility(View.VISIBLE);
                relay4.setVisibility(View.INVISIBLE);
                calendarlayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 450));
                scheduleEt.setText(Bookingdate + "  " + String.format("%02d:%02d", starthour, startminute) + " - " + String.format("%02d:%02d", finishhour, finishminute));
            }
        });

        confirmsetschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!checkplace() | !checkpriceperstudent()){
//
//                    tillPlaceTextlayout.setAnimation(anim);
//                    PriceperStudentTextlayout.setAnimation(anim);
//                    checkplace();
//                    checkpriceperstudent();
//                    vibrator.vibrate(120);
//                    return;
//                }else {
                    showProgressDialog();
                    String course_namest = tillClassnameTextlayout.getEditText().getText().toString();
                    String target_university = tillUniversityTextlayout.getEditText().getText().toString();
                    String target_major = tillMajormakeclassTextlayout.getEditText().getText().toString();
                    String target_year = yearsmakeclass;
                    String term = termmakeclassEt.getText().toString();
                    String level = tilllevelTextlayout.getEditText().getText().toString();
                    String totalstudent = tilltotalnumberstudentTextlayout.getEditText().getText().toString();
                    String datest = bookingdate;
                    String descriptionst = descriptionEt.getText().toString();
                    String tags = tagmakeclassEt.getText().toString();
                    String start_timest = bookingtimestart;
                    String finish_timest = bookingtimefinish;
                    sendOject o = new sendOject();
                    String user_id = o.getUser_id();
                    String price_per_studentst = PriceperStudentTextlayout.getEditText().getText().toString();
                    String place = tillPlaceTextlayout.getEditText().getText().toString();


                    File originalFile = FileUtils.getFile(MakeclassActivity.this, resultUri);
                    RequestBody filepart = RequestBody.create
                            (MediaType.parse(getContentResolver().getType(resultUri)), originalFile);

                    MultipartBody.Part file = MultipartBody.Part.createFormData("course_image", originalFile.getName(), filepart);

                    RequestBody course_nameR = RequestBody.create(MultipartBody.FORM, course_namest);
                    RequestBody target_universityR = RequestBody.create(MultipartBody.FORM, target_university);
                    RequestBody target_majorR = RequestBody.create(MultipartBody.FORM, target_major);
                    RequestBody target_yearR = RequestBody.create(MultipartBody.FORM, target_year);
                    RequestBody termR = RequestBody.create(MultipartBody.FORM, term);
                    RequestBody levelR = RequestBody.create(MultipartBody.FORM, level);
                    RequestBody totalstudentR = RequestBody.create(MultipartBody.FORM, totalstudent);
                    RequestBody dateR = RequestBody.create(MultipartBody.FORM, datest);
                    RequestBody descriptionR = RequestBody.create(MultipartBody.FORM, descriptionst);
                    RequestBody tagsR = RequestBody.create(MultipartBody.FORM, tags);
                    RequestBody start_timeR = RequestBody.create(MultipartBody.FORM, start_timest);
                    RequestBody finish_timeR = RequestBody.create(MultipartBody.FORM, finish_timest);
                    RequestBody user_idR = RequestBody.create(MultipartBody.FORM, user_id);
                    RequestBody price_per_studentR = RequestBody.create(MultipartBody.FORM, price_per_studentst);
                    RequestBody placeR = RequestBody.create(MultipartBody.FORM, place);


                    mAPIService.addClass(course_nameR, dateR, descriptionR, start_timeR, finish_timeR, user_idR, price_per_studentR, target_universityR, target_majorR,
                            target_yearR, termR, levelR, totalstudentR, tagsR, placeR, file).enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            Log.w(TAG, "onResponse: " + response);
                            if (response.isSuccessful()) {
                                Log.w(TAG, "onResponse: " + response.body());
                                dismissProgressDialog();
                                showProgressDialogSuccess();
                            } else {
                                dismissProgressDialog();
                                Log.w(TAG, "onResponse Fail: " + response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Log.w(TAG, "onFailure: " + t.getMessage());
                            dismissProgressDialog();
                        }
                    });
//                }
            }
        });


        today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                today = date;
                calSelected = Calendar.getInstance();
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

                calSelected.set(date.getYear(), date.getMonth(), date.getDate());

                mImageGeneratedDateicon = mImageGenerator.generateDateImage(calSelected, R.drawable.empty_calendar);
                mDisplayGeneratorImage.setImageBitmap(mImageGeneratedDateicon);

//                Toast.makeText(MakeclassActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            makeclassuploadpicture.setImageURI(resultUri);
            makeclassuploadpicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        if (makeclassuploadpicture.getDrawable() == null) {
            relayuploadpicture.setVisibility(View.VISIBLE);
            makeclassuploadpicture.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private void showProgressDialogSuccess() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("You're class was added!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(MakeclassActivity.this, Main2Activity.class));
                finish();
            }
        });
    }

    private boolean checkClassname() {
        if (tillClassnameTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tillClassnameTextlayout.setErrorEnabled(true);
            tillClassnameTextlayout.setError("Please enter a Class title");
            return false;
        }
        tillClassnameTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checktargetUniversity() {
        if (tillUniversityTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tillUniversityTextlayout.setErrorEnabled(true);
            tillUniversityTextlayout.setError("Please enter a University");
            return false;
        }
        tillUniversityTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checktargetmajor() {
        if (tillMajormakeclassTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tillMajormakeclassTextlayout.setErrorEnabled(true);
            tillMajormakeclassTextlayout.setError("Please enter a Major");
            return false;
        }
        tillMajormakeclassTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkterm() {
        if (termmakeclassEt.getText().toString().trim().isEmpty()) {
            termmakeclassEt.setError("Please select term");
            return false;
        }
        return true;
    }

    private boolean checklevel() {
        if (tilllevelTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tilllevelTextlayout.getEditText().setError("Please enter a level of difficult");
            return false;
        }
        return true;
    }

    private boolean checktotalnumber() {
        if (tilltotalnumberstudentTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tilltotalnumberstudentTextlayout.getEditText().setError("Please enter a Total number of student");
            return false;
        }
        return true;
    }

    private boolean checkdescription() {
        if (descriptionEt.getText().toString().trim().isEmpty()) {
            descriptionEt.setError("Please enter a description");
            return false;
        }
        return true;
    }

    private boolean checktagmakeclass() {
        if (tagmakeclassEt.getText().toString().trim().isEmpty()) {
            tagmakeclassEt.setError("Please select tag");
            return false;
        }
        return true;
    }

    private boolean checkplace() {
        if (tillPlaceTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tillPlaceTextlayout.getEditText().setError("Please select tag");
            return false;
        }
        return true;
    }

    private boolean checkpriceperstudent() {
        if (PriceperStudentTextlayout.getEditText().getText().toString().trim().isEmpty()) {
            tillPlaceTextlayout.getEditText().setError("Please select tag");
            return false;
        }
        return true;
    }
}
