package com.example.bee.upint2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager mCallbackManager;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    Button facebookBt;
    String type;
    Button teacherBt,studentBt;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  TextView helo = (TextView) findViewById(R.id.hello);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.bee.upint2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                helo.setText(""+Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

      Button gotoLogin = (Button) findViewById(R.id.signin);
      gotoLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (!teacherBt.hasFocus() && !studentBt.hasFocus()){
                  showProgressDialog();
                  Toast.makeText(MainActivity.this, "Please select who are you", Toast.LENGTH_SHORT).show();
                  dismissProgressDialog();
              }else {
                  Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                  intent.putExtra("Usertype_login", type);
                  startActivity(intent);
              }
          }
      });
      TextView gotoRis = (TextView) findViewById(R.id.signup);
      gotoRis.setClickable(true);
        SpannableString content = new SpannableString("Signup for UPint");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        gotoRis.setText(content);
      gotoRis.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (!teacherBt.hasFocus() && !studentBt.hasFocus()){
                  showProgressDialog();
                  Toast.makeText(MainActivity.this, "Please select who are you", Toast.LENGTH_SHORT).show();
                  dismissProgressDialog();
              }else {
              Intent intent = new Intent(MainActivity.this,Register.class);
              intent.putExtra("Usertype", type);
              startActivity(intent);
              }
          }
      });

        TextView agree = (TextView) findViewById(R.id.agree);
        teacherBt = (Button) findViewById(R.id.teacherBt);
        studentBt = (Button) findViewById(R.id.studentBt);
        teacherBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                teacherBt.setFocusable(true);
                teacherBt.setFocusableInTouchMode(true);
                teacherBt.requestFocus();
                if (teacherBt.isFocused()){
                    type = "Teacher";
                }
                studentBt.clearFocus();
                Log.w(TAG, "User type: " + type);
                return false;
            }
        });

        studentBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                studentBt.setFocusable(true);
                studentBt.setFocusableInTouchMode(true);
                studentBt.requestFocus();
                if (studentBt.isFocused()){
                    type = "Student";
                }
                teacherBt.clearFocus();
                Log.w(TAG, "User type: " + type);
                return false;
            }
        });
        agree.setClickable(true);
        agree.setMovementMethod(
                LinkMovementMethod.getInstance()
        );
        String text = "<a href='https://www.facebook.com/policies'>I agree to the term of Service and Privacy Policy including Cookies use</a>";
        agree.setText(Html.fromHtml(text));

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        facebookBt = (Button) findViewById(R.id.facebookLog);
        facebookBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!teacherBt.hasFocus() && !studentBt.hasFocus()) {
                    Toast.makeText(MainActivity.this, "Please select who are you", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressDialog();
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
                    LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                            handleFacebookAccessToken(loginResult.getAccessToken());
                            getUserDetailsFromFB(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "facebook:onCancel");
                            dismissProgressDialog();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.d(TAG, "facebook:onError", error);
                            dismissProgressDialog();
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void updateUI(FirebaseUser user) {
    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(MainActivity.this, "You're Logged in", Toast.LENGTH_LONG);
//                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                            startActivity(intent);
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        mAuth.signOut();
//        LoginManager.getInstance().logOut();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


//        AuthCredential crea1 = PhoneAuthProvider.getCredential("123456465","1111");
            AuthCredential credential = EmailAuthProvider
                    .getCredential("user@example.com", "password1234");

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete();

                        }
                    });
        }
    }

    public void getUserDetailsFromFB(AccessToken accessToken) {

        GraphRequest req=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.w(TAG, "graph request completed");
//                Toast.makeText(getApplicationContext(),"graph request completed",Toast.LENGTH_SHORT).show();
                try{
                    String email =  object.getString("email");
                    String firstname = object.getString("first_name");
                    String lastname = object.getString("last_name");
                    Log.w(TAG, email);
                    Log.w(TAG, firstname);
                    Log.w(TAG, lastname);
                    Intent i = new Intent(MainActivity.this,Register.class);
                    i.putExtra("userFacebookemail", email);
                    i.putExtra("userFacebookfirstname", firstname);
                    i.putExtra("userFacebooklastname", lastname);
                    i.putExtra("Usertype", type);
                    startActivity(i);
                    dismissProgressDialog();
                    LoginManager.getInstance().logOut();

                }catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(),"graph request error : "+e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,first_name,last_name");
        req.setParameters(parameters);
        req.executeAsync();
    }

    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
