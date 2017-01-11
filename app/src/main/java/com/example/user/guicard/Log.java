package com.example.user.guicard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Log extends Activity implements View.OnClickListener {

    private EditText account;
    private EditText password;
    private EditText name;
    private Firebase firebase;

    private Button next;
    private Button back;
    private boolean judge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_m);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        TextView Next = (TextView) findViewById(R.id.myInformationView);
        TextView Back = (TextView) findViewById(R.id.back1);
        account = (EditText)findViewById(R.id.Account);
        password = (EditText)findViewById(R.id.Password);
        name = (EditText)findViewById(R.id.Name);

        next = (Button)findViewById(R.id.ADD);
        back = (Button)findViewById(R.id.back);
        judge = false;

        //修改字體
        Next.setTypeface(font);
        Back.setTypeface(font);

        firebase = new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");
        if(getIntent().getExtras()!=null){
            account.setText(getIntent().getExtras().getString("ACCOUNT"));
            password.setText(getIntent().getExtras().getString("PASSWORD"));
            name.setText(getIntent().getExtras().getString("NAME"));
        }

        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==next){
            firebase.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo user = new UserInfo(account.getText().toString(), password.getText().toString(), name.getText().toString());

                    if (!dataSnapshot.child(user.account).exists()) {
                        judge = true;
                        Intent intent = new Intent(Log.this, Logview.class);
                        intent.putExtra("ACCOUNT", user.account);
                        intent.putExtra("PASSWORD", user.password);
                        intent.putExtra("NAME", user.name);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }else {
                        if(!judge)Toast.makeText(Log.this, "This account has already exists!", Toast.LENGTH_SHORT).show();
                    }

                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }else{
            Intent intent = new Intent(Log.this,LogMain.class);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}



