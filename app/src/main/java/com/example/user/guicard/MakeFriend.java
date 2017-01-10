package com.example.user.guicard;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;


public class MakeFriend extends AppCompatActivity implements View.OnClickListener{

    private Button B1;
    private Button B2;
    private Button B3;
    private Button B4;
    private Button B5;
    private Button B6;
    private Button B7;
    private Button B8;
    private Button B9;
    private Button next;
    private int wantInterest;
    private ProgressDialog imgProgress;

    private Firebase searchFriend;

    UserInfo user;
    UserInfo friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_friend);

        B1 = (Button) findViewById(R.id.CB1);
        B2 = (Button) findViewById(R.id.CB2);
        B3 = (Button) findViewById(R.id.CB3);
        B4 = (Button) findViewById(R.id.CB4);
        B5 = (Button) findViewById(R.id.CB5);
        B6 = (Button) findViewById(R.id.CB6);
        B7 = (Button) findViewById(R.id.CB7);
        B8 = (Button) findViewById(R.id.CB8);
        B9 = (Button) findViewById(R.id.CB9);
        next = (Button) findViewById(R.id.ok);
        TextView OK = (TextView) findViewById(R.id.OK);
        user = new UserInfo(getIntent().getExtras().getString("My Account"));
        wantInterest = 0;
        searchFriend = new Firebase("https://guicard-de0f4.firebaseio.com/").child("SearchFriend");
        imgProgress = new ProgressDialog(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        OK.setTypeface(font);


        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);
        B5.setOnClickListener(this);
        B6.setOnClickListener(this);
        B7.setOnClickListener(this);
        B8.setOnClickListener(this);
        B9.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == B1) {
            if (!B1.isSelected()) {
                B1.setSelected(true);
                wantInterest += 1;
            } else {
                B1.setSelected(false);
                wantInterest -= 1;
            }
        }
        if (view == B2) {
            if (!B2.isSelected()) {
                B2.setSelected(true);
                wantInterest += 2;
            } else {
                B2.setSelected(false);
                wantInterest -= 2;
            }
        }
        if (view == B3) {
            if (!B3.isSelected()) {
                B3.setSelected(true);
                wantInterest += 4;
            } else {
                B3.setSelected(false);
                wantInterest -= 4;
            }
        }
        if (view == B4) {
            if (!B4.isSelected()) {
                B4.setSelected(true);
                wantInterest += 8;
            } else {
                B4.setSelected(false);
                wantInterest -= 8;
            }
        }
        if (view == B5) {
            if (!B5.isSelected()) {
                B5.setSelected(true);
                wantInterest += 16;
            } else {
                B5.setSelected(false);
                wantInterest -= 16;
            }
        }
        if (view == B6) {
            if (!B6.isSelected()) {
                B6.setSelected(true);
                wantInterest += 32;
            } else {
                B6.setSelected(false);
                wantInterest -= 32;
            }
        }
        if (view == B7) {
            if (!B7.isSelected()) {
                B7.setSelected(true);
                wantInterest += 64;
            } else {
                B7.setSelected(false);
                wantInterest -= 64;
            }
        }
        if (view == B8) {
            if (!B8.isSelected()) {
                B8.setSelected(true);
                wantInterest += 128;
            } else {
                B8.setSelected(false);
                wantInterest -= 128;
            }
        }
        if (view == B9) {
            if (!B9.isSelected()) {
                B9.setSelected(true);
            } else {
                B9.setSelected(false);
            }
        }
        if (view == next) {
            imgProgress.setMessage("Search ...");
            imgProgress.show();
            searchFriend.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int searchNum = 1;
                    Map<String, String> map = dataSnapshot.child(Integer.toString(wantInterest)).child(Integer.toString(searchNum)).getValue(Map.class);
                    while(map.get("INVITED") == Boolean.toString(true)){
                        searchNum++;
                        map = dataSnapshot.child(Integer.toString(wantInterest)).child(Integer.toString(searchNum)).getValue(Map.class);
                    }
                    friend = new UserInfo(map.get("USER"));
                    imgProgress.dismiss();
                    Intent intent = new Intent(MakeFriend.this,Myfriend.class);
                    intent.putExtra("My Account", user.account);
                    intent.putExtra("Friend",friend.account);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

        }
    }


}
