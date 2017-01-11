package com.example.user.guicard;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.Map;


public class LogInview extends AppCompatActivity implements View.OnClickListener{

    private Firebase Users;
    private Button next;
    private ProgressDialog imgProgress;
    private String sendLoginAccount;
    private String sendLoginPassword;
    private TextInputLayout nameLayout;
    private EditText account;
    private TextInputLayout nameLayout1;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_resourse);

        next = (Button)findViewById(R.id.ADD);
        nameLayout = (TextInputLayout)findViewById(R.id.Account1);
        account = (EditText)findViewById(R.id.Account);
        nameLayout1 = (TextInputLayout)findViewById(R.id.Password1);
        password = (EditText)findViewById(R.id.Password);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        TextView OK = (TextView) findViewById(R.id.myInformationView);


        //修改字體
        OK.setTypeface(font);
        imgProgress = new ProgressDialog(this);
        next.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {

            if(account.length() == 0){
                nameLayout.setError("Account doesn't input ");
            }
            else{
                nameLayout.setError(null);
            }

            if(password.length() == 0){
                nameLayout1.setError("Password doesn't input ");
            }
            else{
                nameLayout1.setError(null);
            }

            sendLoginAccount = account.getText().toString();
            sendLoginPassword = password.getText().toString();
            imgProgress.setMessage("Loading ...");
            imgProgress.show();
            Users = new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");
            Users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(!dataSnapshot.child(sendLoginAccount).exists()){
                        Toast.makeText(LogInview.this,"No Such Account!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String,String> map = dataSnapshot.child(sendLoginAccount).getValue(Map.class);

                    if(!sendLoginPassword.equals(map.get("PASSWORD"))){
                        Toast.makeText(LogInview.this,"Enter the Wrong Password!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imgProgress.dismiss();
                    Intent intent = new Intent(LogInview.this,Friendlist.class);
                    intent.putExtra("My Account",sendLoginAccount);//處理Activity間資料傳遞(資料名稱,資料內容)
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
}
