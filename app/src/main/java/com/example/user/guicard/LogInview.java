package com.example.user.guicard;


import android.content.Intent;
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
    private EditText Account;
    private EditText PassWord;
    private Button next;
    private Button back;

    private String sendLoginAccount;
    private String sendLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_resourse);

        next = (Button)findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);
        Account = (EditText)findViewById(R.id.Name);
        PassWord = (EditText)findViewById(R.id.Account);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        TextView OK = (TextView) findViewById(R.id.OK);
        TextView Back = (TextView) findViewById(R.id.back1);
        //修改字體
        OK.setTypeface(font);
        Back.setTypeface(font);

        next.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {
            sendLoginAccount = Account.getText().toString();
            sendLoginPassword = PassWord.getText().toString();

            Users = new Firebase("https://guicard-de0f4.firebaseio.com/");
            Users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(!dataSnapshot.child(sendLoginAccount).exists()){
                        Toast.makeText(LogInview.this,"No Such Account!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String,String> map = dataSnapshot.child(sendLoginAccount).getValue(Map.class);

                    if(!sendLoginPassword.equals(map.get("Password"))){
                        Toast.makeText(LogInview.this,"Enter the Wrong Password!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(LogInview.this,LogupSuccess.class);
                    intent.putExtra("MyAccount",sendLoginAccount);//處理Activity間資料傳遞(資料名稱,資料內容)
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
}
