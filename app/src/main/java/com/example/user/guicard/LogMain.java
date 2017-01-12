package com.example.user.guicard;

//登入畫面SignIn SignUp
import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class LogMain extends Activity implements View.OnClickListener {

    private Button In;
    private Button Up;
    private LoginButton fbLogin;
    private CallbackManager callbackManager;
    private UserInfo user;
    private Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.log);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Shine.ttf");
        TextView SignIn = (TextView) findViewById(R.id.SIGNIN);
        TextView SignUp = (TextView) findViewById(R.id.SIGNUP);
        TextView SignIn1 = (TextView) findViewById(R.id.SIGNIN1);
        TextView SignUp1 = (TextView) findViewById(R.id.SIGNUP1);
        fbLogin = (LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        firebase = new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");

        //修改字體Textview
        SignIn.setTypeface(font1);
        SignUp.setTypeface(font1);
        SignIn1.setTypeface(font1);
        SignUp1.setTypeface(font1);


        In = (Button) findViewById(R.id.In);
        Up = (Button) findViewById(R.id.Up);
        In.setOnClickListener(this);
        Up.setOnClickListener(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                user = new UserInfo(loginResult.getAccessToken().getUserId(), profile.getName(), profile.getProfilePictureUri(170, 170),0);
                firebase.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child(user.account).exists()) {
                            Intent intent = new Intent(LogMain.this, Logview.class);
                            intent.putExtra("ACCOUNT", user.account);
                            intent.putExtra("NAME", user.name);
                            intent.putExtra("PROFILE", user.profileUri.toString());
                            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        else{
                            Intent intent = new Intent(LogMain.this,Friendlist.class);
                            intent.putExtra("My Account",user.account);//處理Activity間資料傳遞(資料名稱,資料內容)
                            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
                    @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }


            @Override
        public void onClick(View v){
            if (v == Up) startActivity(new Intent(this, Log.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            else if(v == In)startActivity(new Intent(this, LogInview.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}

