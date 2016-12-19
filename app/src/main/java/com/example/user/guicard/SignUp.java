package com.example.user.guicard;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Firebase firebase;
    private Button next;
    private EditText account;
    private EditText password;
    private EditText name;
    private RadioGroup genderChoose;
    private CheckBox a1;
    private CheckBox a2;
    private CheckBox a3;
    private CheckBox a4;
    private CheckBox a5;
    private CheckBox a6;
    private CheckBox a7;
    private CheckBox a8;
    private int gender;
    private int interest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        next=(Button)findViewById(R.id.next);
        account = (EditText)findViewById(R.id.Account);
        password = (EditText)findViewById(R.id.Password);
        name = (EditText)findViewById(R.id.Name);
        gender = 0;
        genderChoose = (RadioGroup)findViewById(R.id.gender);
        a1 = (CheckBox)findViewById(R.id.a1);
        a2 = (CheckBox)findViewById(R.id.a2);
        a3 = (CheckBox)findViewById(R.id.a3);
        a4 = (CheckBox)findViewById(R.id.a4);
        a5 = (CheckBox)findViewById(R.id.a5);
        a6 = (CheckBox)findViewById(R.id.a6);
        a7 = (CheckBox)findViewById(R.id.a7);
        a8 = (CheckBox)findViewById(R.id.a8);
        interest = 0;
        firebase = new Firebase("https://guicard-de0f4.firebaseio.com/");
        if(getIntent().getExtras()!=null){
            account.setText(getIntent().getExtras().getString("ACCOUNT"));
            password.setText(getIntent().getExtras().getString("PASSWORD"));
            name.setText(getIntent().getExtras().getString("NAME"));
        }

        genderChoose.check(R.id.man);//initial gender
        next.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        firebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo user = new UserInfo(account.getText().toString(), password.getText().toString(), name.getText().toString(), interest, gender);

                if (!dataSnapshot.child(user.account).exists()) {
                    switch (genderChoose.getCheckedRadioButtonId()) {
                        case R.id.man:gender = 0;break;
                        case R.id.woman:gender = 1;break;
                    }

                    if (a1.isChecked()) interest += 1;
                    if (a2.isChecked()) interest += 2;
                    if (a3.isChecked()) interest += 4;
                    if (a4.isChecked()) interest += 8;
                    if (a5.isChecked()) interest += 16;
                    if (a6.isChecked()) interest += 32;
                    if (a7.isChecked()) interest += 64;
                    if (a8.isChecked()) interest += 128;

                    Intent intent = new Intent(SignUp.this, take_picture.class);
                    intent.putExtra("ACCOUNT", user.account);
                    intent.putExtra("PASSWORD", user.password);
                    intent.putExtra("NAME", user.name);
                    intent.putExtra("INTEREST", user.interes);
                    intent.putExtra("GENDER", user.gender);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }else
                    Toast.makeText(SignUp.this, "This account has already exists!", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}