package com.example.bee.upint2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bee.upint2.adapter.ListViewAdapterProvince;
import com.example.bee.upint2.adapter.ListViewAdapterUniversity;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.example.bee.upint2.utils.FileUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Button next1, next2, next3, next4, next5, uploadpicbuttonlibrary, takepicturebutton, cerificationBt, resendcode, next6,save , next8, next9;
    private RelativeLayout relay1, relay2, relay3, relay4, relay5, relay6, relay7, relay8, relay9;
    private ImageView mprofileimage, id_card_image;
    private Uri resultUri, resultUri2;
    private EditText cerificationEt, phonenumberEt, cerificationcode, firstname, lastname,
            email, password, passwordagain, phonenumber, school, major, location_edittext, search_university_edittext, introduce;
    private Vibrator vibrator;
    private Animation anim;
    private String userType, date_graduated;
    private TextInputLayout firstnameTextlayout, lastnameTextlayout, emailTextlayout,
            passwordTextlayout, passwordagainTextlayout, phonenumberTextlayout, schoolTextlayout, majorTextlayout, cetificationcodeTextlayout;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int CAMERA_PIC_REQUEST2 = 1338;
    private android.support.v7.widget.CardView cardview;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabaseRef;
    private DatePicker datepicker;
    private SweetAlertDialog pDialog;
    private ApiService mAPIService;
    private Call<AccessToken> call;
    private AccessToken accessToken;
    private static final String TAG = "RegisterActivity";
    private ListViewAdapterProvince adapter;
    private ListViewAdapterUniversity adapter2;
    private ArrayList<String> provinceList = new ArrayList<>();
    private ArrayList<String> universityList = new ArrayList<>();
    private ListView listView,listView2;
    private Animation righttoleft, centertoleft;
    private ProgressBar progressBar;
    private RelativeLayout id_card_photo_mode, id_card_photo_gallery, image_shutter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set language
        String languageToLoad  = "en_US"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale.ENGLISH; //set locale language to english
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_register);


        //disable keyboard onclick layout
        RelativeLayout activity_login_layout = findViewById(R.id.activity_register_layout);
        activity_login_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });


        //datepicker
        datepicker = (DatePicker) findViewById(R.id.datePicker);
        //extra string come from MainActivity
        Bundle bundle = getIntent().getExtras();
        userType = bundle.getString("Usertype");

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setProgress(11);
        if (userType.equals("Student")){
            progressBar.setProgress(20);
        }
        progressBar.setProgressTintList(ColorStateList.valueOf(Color
                .parseColor("#98c428")));

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        //TextinputLayout
        firstnameTextlayout = (TextInputLayout) findViewById(R.id.firstnameTextlayout);
        lastnameTextlayout = (TextInputLayout) findViewById(R.id.lastnameTextlayout);
        emailTextlayout = (TextInputLayout) findViewById(R.id.emailTextlayout);
        passwordTextlayout = (TextInputLayout) findViewById(R.id.passwordTextlayout);
        passwordagainTextlayout = (TextInputLayout) findViewById(R.id.passwordagainTextlayout);
        phonenumberTextlayout = (TextInputLayout) findViewById(R.id.phonenumberTextlayout);
        schoolTextlayout = (TextInputLayout) findViewById(R.id.schoolTextlayout);
        majorTextlayout = (TextInputLayout) findViewById(R.id.majorTextlayout);
        cetificationcodeTextlayout = (TextInputLayout) findViewById(R.id.cerificationcodeTextlayout);

        //Animation
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
        anim.setRepeatCount(2);

        //vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //auth firebase
        mAuth = FirebaseAuth.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        //EditText
        cerificationEt = (EditText) findViewById(R.id.cerificationcode);
        cerificationEt.setVisibility(View.GONE);
        phonenumberEt = (EditText) findViewById(R.id.phonenumber);
        cerificationcode = (EditText) findViewById(R.id.cerificationcode);
        firstname = (EditText) findViewById(R.id.firstnameEt);
        lastname = (EditText) findViewById(R.id.lastnameEt);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        passwordagain = (EditText) findViewById(R.id.passwordagain);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        school = (EditText) findViewById(R.id.school);
        school.setOnEditorActionListener(new DoneOnEditorActionListener());
        major = (EditText) findViewById(R.id.major);
        introduce = (EditText) findViewById(R.id.introduce);

        //Button
        next1 = (Button) findViewById(R.id.nextbt1);
        next2 = (Button) findViewById(R.id.nextbt2);
        next3 = (Button) findViewById(R.id.nextbt3);
        next3.setVisibility(View.GONE);
        next4 = (Button) findViewById(R.id.nextbt4);
        next5 = (Button) findViewById(R.id.nextbt5);
        next8 = findViewById(R.id.nextbtregion3);
        next9 = findViewById(R.id.nextbtregion4);
        uploadpicbuttonlibrary = (Button) findViewById(R.id.uploadlibrary);
        takepicturebutton = (Button) findViewById(R.id.takepicture);
        cerificationBt = (Button) findViewById(R.id.cerification);
//        resendcode = (Button) findViewById(R.id.resendcode);

        //Relay layout
        relay1 = (RelativeLayout) findViewById(R.id.relay1);
        relay2 = (RelativeLayout) findViewById(R.id.relay2);
        relay3 = (RelativeLayout) findViewById(R.id.relay3);
        relay4 = (RelativeLayout) findViewById(R.id.relay4);
        relay5 = (RelativeLayout) findViewById(R.id.relay5);
        relay8 = findViewById(R.id.student_resume_layout3);
        relay9 = findViewById(R.id.student_resume_layout4);
        cardview = (CardView) findViewById(R.id.cardview);
        id_card_photo_mode = (RelativeLayout) findViewById(R.id.id_card_photo_mode);
        id_card_photo_gallery = (RelativeLayout) findViewById(R.id.id_card_photo_gallery);
        id_card_image = (ImageView) findViewById(R.id.id_card_image);
        image_shutter = (RelativeLayout) findViewById(R.id.image_shutter);

        //set visible view
        relay1.setVisibility(View.VISIBLE);
        relay2.setVisibility(View.INVISIBLE);
        relay3.setVisibility(View.INVISIBLE);
        relay4.setVisibility(View.INVISIBLE);
        relay5.setVisibility(View.INVISIBLE);

        righttoleft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.righttoleftanim);
        centertoleft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.centertoleft);
        relay1.startAnimation(righttoleft);

        id_card_photo_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_shutter.setVisibility(View.GONE);
                id_card_image.setVisibility(View.VISIBLE);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST2);
            }
        });

        id_card_photo_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_shutter.setVisibility(View.GONE);
                id_card_image.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        //set on click
        //profile image
        mprofileimage = (ImageView) findViewById(R.id.profileimage);
        mprofileimage.setVisibility(View.GONE);
        uploadpicbuttonlibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadpicbuttonlibrary.setVisibility(View.GONE);
                takepicturebutton.setVisibility(View.GONE);
                mprofileimage.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        takepicturebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadpicbuttonlibrary.setVisibility(View.GONE);
                takepicturebutton.setVisibility(View.GONE);
                mprofileimage.setVisibility(View.VISIBLE);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);


            }
        });
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFirstname() || !checkLastname()) {
                    firstname.setAnimation(anim);
                    lastname.setAnimation(anim);
                    checkFirstname();
                    checkLastname();
                    vibrator.vibrate(120);
                    return;
                } else {
                    progressBar.setProgress(22);
                    if (userType.equals("Student")){
                        progressBar.setProgress(40);
                    }
                    relay1.setVisibility(View.GONE);
                    relay1.startAnimation(centertoleft);
                    relay2.setVisibility(View.VISIBLE);
                    relay2.startAnimation(righttoleft);
                }

            }
        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEmail() || !checkpassword() || !checkpasswordagain()) {
                    email.setAnimation(anim);
                    password.setAnimation(anim);
                    passwordagain.setAnimation(anim);
                    checkEmail();
                    checkpassword();
                    checkpasswordagain();
                    vibrator.vibrate(120);
                    return;
                } else {
                    showProgressDialog();
                    mAPIService = ApiUtils.getAPIService();
                    mAPIService.checkUserEmailforRegister(email.getText().toString()).enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            if (response.isSuccessful()) {
                                dismissProgressDialog();
                                showProgressDialogSuccessEmailduplicate();
                            }else {
                                dismissProgressDialog();
                                showProgressDialogRegisterEmailDuplicate();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            dismissProgressDialog();
                            showProgressDialogWarningConnection();


                        }
                    });
                }
            }
        });
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkcerificationcode()) {
                    cerificationcode.setAnimation(anim);
                    vibrator.vibrate(120);
                    return;
                } else {
//                    String code = cerificationcode.getText().toString();
//                    PhoneAuthCredential credential =
//                            PhoneAuthProvider.getCredential(phoneVerificationId, code);
//                    signInWithPhoneAuthCredential(credential);
                    relay3.startAnimation(centertoleft);
                    relay3.setVisibility(View.GONE);
                    relay4.setVisibility(View.VISIBLE);
                    relay4.startAnimation(righttoleft);
                }

            }
        });
        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkschool() || !checkmajor()) {
                    school.setAnimation(anim);
                    major.setAnimation(anim);
                    checkschool();
                    checkmajor();
                    vibrator.vibrate(120);
                    return;
                }
                relay4.startAnimation(centertoleft);
                relay4.setVisibility(View.GONE);
                progressBar.setProgress(55);
                if (userType.equals("Student")){
                    progressBar.setProgress(100);
                }
                relay5.setVisibility(View.VISIBLE);
                relay5.startAnimation(righttoleft);
            }
        });
        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("Teacher")) {
                    progressBar.setProgress(66);
                    relay5.startAnimation(centertoleft);
                    relay5.setVisibility(View.GONE);
                    relay6.setVisibility(View.VISIBLE);
                    relay6.startAnimation(righttoleft);

                } else {
                    showProgressDialog();
                    mAPIService = ApiUtils.getAPIService();
                    String first_namest = firstname.getText().toString();
                    String last_namest = lastname.getText().toString();
                    String emailst = email.getText().toString();
                    String passwordst = password.getText().toString();
                    String repasswordst = passwordagain.getText().toString();
                    String phonenumberst = "0" + phonenumber.getText().toString();
                    String schoolst = school.getText().toString();
                    String majorst = major.getText().toString();
                    String statest = userType;
                    String date_graduatedst = date_graduated;


                    RequestBody firstnameR = RequestBody.create(MultipartBody.FORM, first_namest);
                    RequestBody lastnameR = RequestBody.create(MultipartBody.FORM, last_namest);
                    RequestBody emailR = RequestBody.create(MultipartBody.FORM, emailst);
                    RequestBody passwordR = RequestBody.create(MultipartBody.FORM, passwordst);
                    RequestBody repasswordR = RequestBody.create(MultipartBody.FORM, repasswordst);
                    RequestBody phonenumberR = RequestBody.create(MultipartBody.FORM, phonenumberst);
                    RequestBody schoolR = RequestBody.create(MultipartBody.FORM, schoolst);
                    RequestBody majorR = RequestBody.create(MultipartBody.FORM, majorst);
                    RequestBody stateR = RequestBody.create(MultipartBody.FORM, statest);
                    RequestBody date_graduatedR = RequestBody.create(MultipartBody.FORM, date_graduatedst);

                    File originalFile = FileUtils.getFile(Register.this, resultUri);
                    RequestBody filepart = RequestBody.create
                            (MediaType.parse(getContentResolver().getType(resultUri)), originalFile);

                    MultipartBody.Part file = MultipartBody.Part.createFormData("image", originalFile.getName(), filepart);


                    mAPIService.register(firstnameR, lastnameR, emailR, passwordR, repasswordR, phonenumberR,
                            schoolR, majorR, stateR, date_graduatedR, file).enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            Log.w(TAG, "onResponse: " + response);
                            if (response.isSuccessful()) {
                                Log.w(TAG, "onResponse: " + response.body());
                                dismissProgressDialog();
                                accessToken = response.body();
                                Log.w(TAG, "onResponse: " + response.body().getError());
                                showProgressDialogSuccessRegister();
                            } else {
                                dismissProgressDialog();
                                showProgressDialogRegisterEmailDuplicate();
                                Log.w(TAG, "onResponse Fail: " + response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Log.w(TAG, "onFailure: " + t.getMessage());
                            dismissProgressDialog();
                            showProgressDialogWarningConnection();
                        }
                    });
                }
            }
        });
        next8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(100);
                relay8.startAnimation(centertoleft);
                relay8.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                relay9.setVisibility(View.VISIBLE);
                relay9.startAnimation(righttoleft);
            }
        });
        cerificationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkphonenumber()) {
                    phonenumber.setAnimation(anim);
                    vibrator.vibrate(120);
                    return;
                } else {
                    cerificationEt.setVisibility(View.VISIBLE);
                    cetificationcodeTextlayout.setVisibility(View.VISIBLE);
                    cetificationcodeTextlayout.requestFocus();
                    cerificationBt.setVisibility(View.INVISIBLE);
//                    resendcode.setVisibility(View.VISIBLE);
                    next3.setVisibility(View.VISIBLE);
                    setUpVerificatonCallbacks();
                    String phoneNumber = "+66" + phonenumberEt.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, Register.this, verificationCallbacks);
                }
            }
        });
        email.setText(bundle.getString("userFacebookemail"));
        firstname.setText(bundle.getString("userFacebookfirstname"));
        lastname.setText(bundle.getString("userFacebooklastname"));
        date_graduated = getDateFromDatePicker(datepicker);

        //add resume
        TextView location = findViewById(R.id.location);
        float density = getResources().getDisplayMetrics().density;
        Drawable drawable_location = getResources().getDrawable(R.drawable.place_green);
        int width = Math.round(18 * density);
        int height = Math.round(22 * density);
        drawable_location.setBounds(0,0,width,height);
        location.setCompoundDrawables(null,null,drawable_location,null);
        location.setCompoundDrawablePadding(5);

        EditText search_university_edittextr = findViewById(R.id.search_university_edittext);
        float density_search = getResources().getDisplayMetrics().density;
        Drawable drawable_search = getResources().getDrawable(R.drawable.ic_search_black);
        int width_search = Math.round(28 * density_search);
        int height_search = Math.round(28 * density_search);
        drawable_search.setBounds(0,0,width_search,height_search);
        search_university_edittextr.setCompoundDrawables(drawable_search,null,null,null);
        search_university_edittextr.setCompoundDrawablePadding(5);

        relay6 = findViewById(R.id.student_resume_layout1);
        relay7 = findViewById(R.id.student_resume_layout2);

        next6 = findViewById(R.id.nextbtregion1);
        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(77);
                relay6.startAnimation(centertoleft);
                relay6.setVisibility(View.GONE);
                relay7.setVisibility(View.VISIBLE);
                relay7.startAnimation(righttoleft);
                location_edittext.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(search_university_edittext, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        save = findViewById(R.id.resume_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(88);
                relay7.startAnimation(centertoleft);
                relay7.setVisibility(View.GONE);
                relay8.setVisibility(View.VISIBLE);
                relay8.startAnimation(righttoleft);
            }
        });

        listView = findViewById(R.id.province_list_view);
        listView2 = findViewById(R.id.university_list_view);

        location_edittext = findViewById(R.id.location);
        search_university_edittext = findViewById(R.id.search_university_edittext);

        location_edittext.addTextChangedListener(mTextEditorWatcher);
        search_university_edittext.addTextChangedListener(mTextEditorWatcher2);

        provinceList.addAll(Arrays.asList("Amnat Charoen", "Ang Thong", "Bangkok", "Bueng Kan",
                "Buriram", "Chachoengsao", "Chai Nat", "Chaiyaphum",
                "Chanthaburi", "Chiang Mai", "Chiang Rai", "Chonburi",
                "Chumphon", "Kalasin", "Kamphaeng Phet", "Kanchanaburi",
                "Khon Kaen", "Krabi", "Lampang", "Lamphun",
                "Loei", "Lopburi", "Mae Hong Son", "Maha Sarakham",
                "Mukdahan", "Nakhon Nayok", "Nakhon Pathom", "Nakhon Phanom",
                "Nakhon Ratchasima", "Nakhon Sawan", "Nakhon Si Thammarat", "Nan",
                "Narathiwat", "Nong Bua Lam Phu", "Nong Khai", "Nonthaburi",
                "Pathum Thani", "Pattani", "Phang Nga", "Phatthalung",
                "Phayao", "Phetchabun", "Phetchaburi", "Phichit",
                "Phitsanulok", "Phra Nakhon Si Ayutthaya", "Phrae", "Phuket",
                "Prachinburi", "Prachuap Khiri Khan", "Ranong", "Ratchaburi",
                "Rayong", "Roi Et", "Sa Kaeo", "Sakon Nakhon",
                "Samut Prakan", "Samut Sakhon", "Samut Songkhram", "Saraburi",
                "Satun", "Sing Buri", "Sisaket", "Songkhla",
                "Sukhothai", "Suphan Buri", "Surat Thani", "Surin",
                "Tak", "Trang", "Trat", "Ubon Ratchathani",
                "Udon Thani", "Uthai Thani", "Uttaradit", "Yala", "Yasothon"));

        adapter = new ListViewAdapterProvince(this, provinceList);
        listView.setAdapter(adapter);

        universityList.addAll(Arrays.asList("Kalasin University", "Maejo University", "Mahasarakham University",
                "Nakhon Phanom University", "Naresuan University", "National Institute of Development Administration",
                "Pathumwan Institute of Technology", "Prince of Songkla University", "Princess of Naradhiwas University",
                "Ramkhamhaeng University", "Silpakorn University", "Srinakharinwirot University",
                "Sukhothai Thammathirat Open University", "Ubon Ratchathani University", "Burapha University",
                "Chiang Mai University", "Chulalongkorn University", "Chulabhorn Royal Academy of Science",
                "Kasetsart University", "Khon Kaen University", "King Mongkut's Institute of Technology Ladkrabang",
                "King Mongkut's University of Technology North Bangkok", "King Mongkut's University of Technology Thonburi", "Mae Fah Luang University",
                "Mahachulalongkornrajavidyalaya University", "Mahamakut Buddhist University", "Mahidol University",
                "Navamindradhiraj University", "University of Phayao", "Princess Galyani Vadhana Institute of Music",
                "Suan Dusit University", "Suranaree University of Technology", "Thaksin University",
                "Thammasat University", "Walailak University", "Bansomdejchaopraya Rajabhat University",
                "Buri Ram Rajabhat University", "Chaiyaphum Rajabhat University", "Chandrakasem Rajabhat University",
                "Chiang Mai Rajabhat University", "Chiang Rai Rajabhat University", "Dhonburi Rajabhat University",
                "Kamphaeng Phet Rajabhat University", "Kanchanaburi Rajabhat University", "Lampang Rajabhat University",
                "Loei Rajabhat University", "Maha Sarakham Rajabhat University", "Muban Chom Bung Rajabhat University",
                "Nakhon Pathom Rajabhat University", "Nakhon Ratchasima Rajabhat University", "Nakhon Sawan Rajabhat University",
                "Nakhon Si Thammarat Rajabhat University", "Valaya Alongkorn Rajabhat University", "Phetchabun Rajabhat University",
                "Phetchaburi Rajabhat University", "Phranakhon Rajabhat University", "Phranakhon Si Ayutthaya Rajabhat University",
                "Phuket Rajabhat University", "Pibulsongkram Rajabhat University", "Rajanagarindra Rajabhat University",
                "Rambhai Barni Rajabhat University", "Roi Et Rajabhat University", "Sakon Nakhon Rajabhat University",
                "Sisaket Rajabhat University", "Songkhla Rajabhat University", "Suan Sunandha Rajabhat University",
                "Suratthani Rajabhat University", "Surin Rajabhat University", "Thepsatri Rajabhat University",
                "Ubon Ratchathani Rajabhat University", "Udon Thani Rajabhat University", "Uttaradit Rajabhat University",
                "Yala Rajabhat University", "Rajamangala University of Technology Isan", "Rajamangala University of Technology Krungthep",
                "Rajamangala University of Technology Lanna", "Rajamangala University of Technology Phra Nakhon", "Rajamangala University of Technology Rattanakosin",
                "Rajamangala University of Technology Srivijaya", "Rajamangala University of Technology Suvarnabhumi", "Rajamangala University of Technology Tawan-ok",
                "Rajamangala University of Technology Thanyaburi", "Bunditpatanasilpa institute", "Civil Aviation Training Center",
                "Phramongkutklao College of Medicine", "Police Nursing College", "Praboromarajchanok Institute",
                "Royal Thai Army Nursing College", "Royal Thai Navy College of Nursing", "The Royal Thai Air Force Nursing College",
                "Chulachomklao Royal Military Academy", "Royal Thai Navy Academy", "Royal Thai Air Force Academy",
                "Royal Police Cadet Academy", "Asia-Pacific International University", "Asian University",
                "Assumption University", "Bangkok University", "Bangkokthonburi University",
                "Chaopraya University", "Christian University", "Dhurakij Pundit University",
                "E-sarn University", "Eastern Asia University", "Hatyai University",
                "Huachiew Chalermprakiet University", "Kasem Bundit University", "Krirk University",
                "Mahanakorn University of Technology", "North Eastern University", "North Chiang Mai University",
                "Pathumthani University", "Payap University", "The University of Central Thailand",
                "Ratchathani University", "Rattana Bundit University", "Saint John's University",
                "Shinawatra University", "Siam University", "South-East Asia University",
                "Sripatum University", "Stamford International University", "Rangsit University",
                "The Far Eastern University", "University of the Thai Chamber of Commerce", "Vongchavalitkul University",
                "Webster University Thailand", "Western University", "Fatoni University",
                "Nation University (Yonok University)", "The Eastern University of Management and Technology", "Thonburi University",
                "North Bangkok University", "Arsom Silp Institute of the Arts", "Bangkok School of Management",
                "Raffles International College(Bangkok)", "Chulabhorn Graduate Institute", "Institute of Technology Ayothaya",
                "Kantana Institute", "Learning Institute For Everyone", "Panyapiwat Institute of Management",
                "Rajapark Institute", "SAE Institute Bangkok", "Thai-Nichi Institute of Technology",
                "Bangkok Suvarnabhumi University", "Bundit Boriharnthurakit College", "Cambridge College (Thailand)",
                "Chalermkarnchana University", "Chiangrai College", "College of Asian Scholars",
                "Dusit Thani College", "International Buddhist College", "Lampang Inter-Tech College",
                "Lumnamping College", "Nakhonratchasima College", "Phanomwan College",
                "Phitsanulok University", "Raffles International College", "Saengtham College",
                "Saint Louis College", "Santapol College", "Siam Technology College",
                "Southeast Bangkok College", "Southern College of Technology", "St Theresa International College",
                "Tapee University", "Thongsook College", "Asian Institute of Technology"
        ));

        adapter2 = new ListViewAdapterUniversity(this, universityList);
        listView2.setAdapter(adapter2);

        Collections.sort(universityList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location_edittext.setText(provinceList.get(position).toString());
                location_edittext.clearComposingText();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                listView.setVisibility(View.INVISIBLE);

            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search_university_edittext.setText(universityList.get(position).toString());
                search_university_edittext.clearComposingText();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                listView2.setVisibility(View.INVISIBLE);
            }
        });
        next9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                mAPIService = ApiUtils.getAPIService();
                String first_namest = firstname.getText().toString();
                String last_namest = lastname.getText().toString();
                String emailst = email.getText().toString();
                String passwordst = password.getText().toString();
                String repasswordst = passwordagain.getText().toString();
                String phonenumberst = "0" + phonenumber.getText().toString();
                String schoolst = school.getText().toString();
                String majorst = major.getText().toString();
                String statest = userType;
                String date_graduatedst = date_graduated;
                String locationst = location_edittext.getText().toString();
                String university_can_teachst = search_university_edittext.getText().toString();
                String introducest = introduce.getText().toString();


                RequestBody firstnameR = RequestBody.create(MultipartBody.FORM, first_namest);
                RequestBody lastnameR = RequestBody.create(MultipartBody.FORM, last_namest);
                RequestBody emailR = RequestBody.create(MultipartBody.FORM, emailst);
                RequestBody passwordR = RequestBody.create(MultipartBody.FORM, passwordst);
                RequestBody repasswordR = RequestBody.create(MultipartBody.FORM, repasswordst);
                RequestBody phonenumberR = RequestBody.create(MultipartBody.FORM, phonenumberst);
                RequestBody schoolR = RequestBody.create(MultipartBody.FORM, schoolst);
                RequestBody majorR = RequestBody.create(MultipartBody.FORM, majorst);
                RequestBody stateR = RequestBody.create(MultipartBody.FORM, statest);
                RequestBody date_graduatedR = RequestBody.create(MultipartBody.FORM, date_graduatedst);
                RequestBody location = RequestBody.create(MultipartBody.FORM, locationst);
                RequestBody university_can_teach = RequestBody.create(MultipartBody.FORM, university_can_teachst);
                RequestBody introduce = RequestBody.create(MultipartBody.FORM, introducest);

                File originalFile = FileUtils.getFile(Register.this, resultUri);
                RequestBody filepart = RequestBody.create
                        (MediaType.parse(getContentResolver().getType(resultUri)), originalFile);
                MultipartBody.Part file = MultipartBody.Part.createFormData("image", originalFile.getName(), filepart);

                File originalFile2 = FileUtils.getFile(Register.this, resultUri2);
                RequestBody filepart2 = RequestBody.create
                        (MediaType.parse(getContentResolver().getType(resultUri2)), originalFile2);
                MultipartBody.Part file2 = MultipartBody.Part.createFormData("id_card", originalFile2.getName(), filepart2);

                mAPIService.Teacherregister(firstnameR, lastnameR, emailR, passwordR, repasswordR, phonenumberR,
                        schoolR, majorR, stateR, date_graduatedR, file, location, university_can_teach, file2, introduce).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        Log.w(TAG, "onResponse Fail: " + response.message());
                        if (response.isSuccessful()) {
                            Log.w(TAG, "onResponse: " + response.body());
                            dismissProgressDialog();
                            accessToken = response.body();
                            Log.w(TAG, "onResponse: " + response.body().getError());
                            showProgressDialogSuccessRegister();
                        } else {
                            dismissProgressDialog();
                            showProgressDialogRegisterEmailDuplicate();
                            Log.w(TAG, "onResponse Fail: " + response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Log.w(TAG, "onFailure: " + t.getMessage());
                        dismissProgressDialog();
                        showProgressDialogWarningConnection();

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mprofileimage.setImageURI(resultUri);
            mprofileimage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri2 = imageUri;
            id_card_image.setImageURI(resultUri2);
            id_card_image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode != RESULT_CANCELED) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                resultUri = getImageUri(Register.this, image);
                mprofileimage.setImageURI(resultUri);
                mprofileimage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
        if (requestCode == CAMERA_PIC_REQUEST2) {
            if (resultCode != RESULT_CANCELED) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                resultUri2 = getImageUri(Register.this, image);
                id_card_image.setImageURI(resultUri2);
                id_card_image.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
        if (mprofileimage.getDrawable() == null) {
            uploadpicbuttonlibrary.setVisibility(View.VISIBLE);
            takepicturebutton.setVisibility(View.VISIBLE);
            mprofileimage.setVisibility(View.GONE);
        }
        else if (id_card_image.getDrawable() == null) {
            image_shutter.setVisibility(View.VISIBLE);
            id_card_image.setVisibility(View.GONE);
        }else {
            Toast.makeText(Register.this, "Picture NOt taken", Toast.LENGTH_LONG);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        showWarningReceiveSMS();

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded;
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;

                    }
                };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        showProgressDialog();
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            final FirebaseUser userphone = FirebaseAuth.getInstance().getCurrentUser();
                            if (userphone != null) {
                                dismissProgressDialog();
                                showProgressDialogSuccess();
                                AuthCredential crea1 = PhoneAuthProvider.getCredential("123456465", "1111");
                                user.reauthenticate(crea1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                userphone.delete();

                                            }
                                        });
                            }
                        } else {
                            dismissProgressDialog();
                            Log.w(TAG, "task fail");
                            showProgressDialogWarning();
                            // The verification code entered was invalid
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Register.this, "Your Certification code is incorrect. Please try again", Toast.LENGTH_LONG);
                                Log.w(TAG, "getException");
                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = "+66" + phonenumberEt.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    private boolean checkFirstname() {
        if (firstname.getText().toString().trim().isEmpty()) {
            firstnameTextlayout.setErrorEnabled(true);
            firstnameTextlayout.setError("Please enter a Name");
            firstname.setError("Please enter a Firstame");
            return false;
        }
        firstnameTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkLastname() {
        if (lastname.getText().toString().trim().isEmpty()) {
            lastnameTextlayout.setErrorEnabled(true);
            lastnameTextlayout.setError("Please enter a Name");
            lastname.setError("Please enter a Lastname");
            return false;
        }
        lastnameTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkEmail() {
        if (email.getText().toString().trim().isEmpty()) {
            emailTextlayout.setErrorEnabled(true);
            emailTextlayout.setError("Please enter a E-mail");
            email.setError("Please enter a E-mail");
            return false;
        }
        if (!isValidEmail(email.getText().toString())) {
            emailTextlayout.setErrorEnabled(true);
            emailTextlayout.setError("Please enter a correct E-mail");
            email.setError("Please enter a correct E-mail");
            return false;
        }
        emailTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkpassword() {
        if (password.getText().toString().trim().isEmpty()) {
            passwordTextlayout.setErrorEnabled(true);
            passwordTextlayout.setError("Please enter a Password");
            return false;
        } else if (password.getText().toString().length() < 6) {
            passwordTextlayout.setErrorEnabled(true);
            passwordTextlayout.setError("Password must more than 5 Characters");
            return false;
        }
        passwordTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkpasswordagain() {
        if (passwordagain.getText().toString().trim().isEmpty()) {
            passwordagainTextlayout.setErrorEnabled(true);
            passwordagainTextlayout.setError("Please enter a Password");
            passwordagain.setError("Please enter a Password");
            return false;
        } else if (!password.getText().toString().equals(passwordagain.getText().toString())) {
            passwordagainTextlayout.setErrorEnabled(true);
            passwordagainTextlayout.setError("Please enter a same Password");
            passwordagain.setError("Please enter a same Password");
            return false;
        }
        passwordagainTextlayout.setErrorEnabled(false);
        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private boolean checkphonenumber() {
        if (phonenumber.getText().toString().trim().isEmpty()) {
            phonenumberTextlayout.setErrorEnabled(true);
            phonenumberTextlayout.setError("Please enter a Phone number");
            phonenumber.setError("Please enter a Phone number");
            return false;
        } else if (phonenumber.getText().toString().length() != 9) {
            phonenumberTextlayout.setErrorEnabled(true);
            phonenumberTextlayout.setError("Please enter a correct Phone number");
            phonenumber.setError("Please enter a correct Phone number");
            return false;
        }
        phonenumberTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkschool() {
        if (school.getText().toString().trim().isEmpty()) {
            schoolTextlayout.setErrorEnabled(true);
            schoolTextlayout.setError("Please enter a School");
            school.setError("Please enter a School");
            return false;
        }
        schoolTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkmajor() {
        if (major.getText().toString().trim().isEmpty()) {
            majorTextlayout.setErrorEnabled(true);
            majorTextlayout.setError("Please enter a Major");
            major.setError("Please enter a Major");
            return false;
        }
        majorTextlayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkcerificationcode() {
        if (cerificationcode.getText().toString().trim().isEmpty()) {
            cetificationcodeTextlayout.setErrorEnabled(true);
            cetificationcodeTextlayout.setError("Please enter a Major");
            cerificationcode.setError("Please enter a Major");
            return false;
        }
        cetificationcodeTextlayout.setErrorEnabled(false);
        return true;
    }

    public static String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return day + "/" + month + "/" + year;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
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

    private void showProgressDialogRegisterEmailDuplicate() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("ERROR!");
        pDialog.setContentText("Your Email has already registered");
        pDialog.setConfirmText("Ok");
        pDialog.show();
    }

    private void showProgressDialogWarning() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("ERROR!");
        pDialog.setContentText("Your certification code is not correct");
        pDialog.setConfirmText("Ok");
        pDialog.show();
    }

    private void showWarningReceiveSMS() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("ERROR!");
        pDialog.setContentText("Your phone number can not receive certification code yet. Please try again");
        pDialog.setConfirmText("Ok");
        pDialog.show();
        cerificationBt.setVisibility(View.VISIBLE);
        cetificationcodeTextlayout.setVisibility(View.GONE);
        next3.setVisibility(View.GONE);
    }

    private void showProgressDialogWarningConnection(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("ERROR!");
        pDialog.setContentText("Connection Failed");
        pDialog.setConfirmText("Ok");
        pDialog.show();
    }
    private void showProgressDialogSuccessEmailduplicate() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("Your email can use for Registration");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                progressBar.setProgress(33);
                if (userType.equals("Student")){
                    progressBar.setProgress(60);
                }
                relay2.setVisibility(View.GONE);
                relay2.startAnimation(centertoleft);
                relay3.setVisibility(View.VISIBLE);
                relay3.startAnimation(righttoleft);
            }
        });
    }

    private void showProgressDialogSuccess() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("You certification code is correct!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                progressBar.setProgress(44);
                if (userType.equals("Student")){
                    progressBar.setProgress(80);
                }
                relay3.startAnimation(centertoleft);
                relay3.setVisibility(View.GONE);
                relay4.setVisibility(View.VISIBLE);
                relay4.startAnimation(righttoleft);
            }
        });
    }
    private void showProgressDialogSuccessRegister() {
        pDialog =  new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("Registration Successful");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(Register.this, LoginActivity.class));
                finish();
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //textwatcher province
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            listView.setVisibility(View.VISIBLE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        public void afterTextChanged(Editable s)
        {
            String who = location_edittext.getText().toString().toLowerCase(Locale.getDefault());
            adapter.myFilter(who);
            listView.setAdapter(adapter);
        }
    };


    //textwatcher university
    private final TextWatcher mTextEditorWatcher2 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            listView2.setVisibility(View.VISIBLE);
            search_university_edittext.setTextColor(Color.parseColor("#98c428"));
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        public void afterTextChanged(Editable s)
        {
            String who = search_university_edittext.getText().toString().toLowerCase(Locale.getDefault());
            adapter2.myFilter(who);
            listView2.setAdapter(adapter2);
        }
    };

    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

}
