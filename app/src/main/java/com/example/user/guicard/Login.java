package com.example.user.guicard;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private Firebase Users;

    private EditText loginAccount;
    private EditText loginPassword;
    private Button login;

    private String sendLoginAccount;
    private String sendLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.Login);
        loginAccount=(EditText)findViewById(R.id.loginAccount);
        loginPassword=(EditText)findViewById(R.id.loginPassword);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendLoginAccount=loginAccount.getText().toString();
        sendLoginPassword=loginPassword.getText().toString();

        Users = new Firebase("https://guicard-de0f4.firebaseio.com/");
        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child(sendLoginAccount).exists()){
                    Toast.makeText(Login.this,"No Such Account!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String,String> map = dataSnapshot.child(sendLoginAccount).getValue(Map.class);

                if(!sendLoginPassword.equals(map.get("PASSWORD"))){
                    Toast.makeText(Login.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Intent intent=new Intent(Login.this, UserInterface.class);
                intent.putExtra("MyAccount", sendLoginAccount);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));**/

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
