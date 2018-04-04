package com.example.bee.upint2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.example.bee.upint2.network.TokenManager;
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
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Button next1, next2, next3, next4, next5, uploadpicbuttonlibrary, takepicturebutton, cerificationBt, resendcode;
    private RelativeLayout relay1, relay2, relay3, relay4, relay5;
    private ImageView mprofileimage;
    private Uri resultUri;
    private EditText cerificationEt, phonenumberEt, cerificationcode, firstname, lastname,
            email, password, passwordagain, phonenumber, school, major;
    private Vibrator vibrator;
    private Animation anim;
    private String userType, date_graduated;
    private TextInputLayout firstnameTextlayout, lastnameTextlayout, emailTextlayout,
            passwordTextlayout, passwordagainTextlayout, phonenumberTextlayout, schoolTextlayout, majorTextlayout, cetificationcodeTextlayout;
    private static final int CAMERA_PIC_REQUEST = 1337;
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
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


        //datepicker
        datepicker = (DatePicker) findViewById(R.id.datePicker);


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
        major = (EditText) findViewById(R.id.major);
        //Button
        next1 = (Button) findViewById(R.id.nextbt1);
        next2 = (Button) findViewById(R.id.nextbt2);
        next3 = (Button) findViewById(R.id.nextbt3);
        next3.setVisibility(View.GONE);
        next4 = (Button) findViewById(R.id.nextbt4);
        next5 = (Button) findViewById(R.id.nextbt5);
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
        cardview = (CardView) findViewById(R.id.cardview);
        //set visible view
        relay1.setVisibility(View.VISIBLE);
        relay2.setVisibility(View.INVISIBLE);
        relay3.setVisibility(View.INVISIBLE);
        relay4.setVisibility(View.INVISIBLE);
        relay5.setVisibility(View.INVISIBLE);
        //profile image
        mprofileimage = (ImageView) findViewById(R.id.profileimage);
        mprofileimage.setVisibility(View.GONE);
        //set on click button
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
                    relay2.setVisibility(View.VISIBLE);
                    relay1.setVisibility(View.GONE);
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
                                relay3.setVisibility(View.VISIBLE);
                                relay2.setVisibility(View.GONE);
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
                    String code = cerificationcode.getText().toString();
                    PhoneAuthCredential credential =
                            PhoneAuthProvider.getCredential(phoneVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
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
                relay5.setVisibility(View.VISIBLE);
                relay4.setVisibility(View.GONE);
            }
        });
        next5.setOnClickListener(new View.OnClickListener() {
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
                    cerificationBt.setVisibility(View.INVISIBLE);
//                    resendcode.setVisibility(View.VISIBLE);
                    next3.setVisibility(View.VISIBLE);
                    setUpVerificatonCallbacks();
                    String phoneNumber = "+66" + phonenumberEt.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, Register.this, verificationCallbacks);
                }
            }
        });
        //extra string come from MainActivity
        Bundle bundle = getIntent().getExtras();
        userType = bundle.getString("Usertype");
        email.setText(bundle.getString("userFacebookemail"));
        firstname.setText(bundle.getString("userFacebookfirstname"));
        lastname.setText(bundle.getString("userFacebooklastname"));
        date_graduated = getDateFromDatePicker(datepicker);
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
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode != RESULT_CANCELED) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                resultUri = getImageUri(Register.this, image);
                mprofileimage.setImageURI(resultUri);
                mprofileimage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
        if (mprofileimage.getDrawable() == null) {
            uploadpicbuttonlibrary.setVisibility(View.VISIBLE);
            takepicturebutton.setVisibility(View.VISIBLE);
            mprofileimage.setVisibility(View.GONE);
        } else {
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
                            relay4.setVisibility(View.VISIBLE);
                            relay3.setVisibility(View.GONE);
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
                                                relay4.setVisibility(View.VISIBLE);
                                                relay3.setVisibility(View.GONE);

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
    }

    private void showProgressDialogSuccess() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("You certification code is correct!");
        pDialog.show();
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

}
