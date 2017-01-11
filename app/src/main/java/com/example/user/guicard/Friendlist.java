package com.example.user.guicard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Friendlist extends Activity {
    private Button ADD;
    private Button MYINFORMATION;
    private Button INVITED;
    private Typeface font;
    private TextView title;
    private TextView OK;
    private TextView MYINFORTEXT;
    private UserInfo user;
    private GridLayout imageLayout;
    private int friendNum;
    private ImageView[] v = new ImageView[6];

    private Firebase myInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);

        font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        title = (TextView)findViewById(R.id.title);
        OK = (TextView)findViewById(R.id.OK);
        MYINFORTEXT = (TextView)findViewById(R.id.MYINFORTEXT);
        title.setTypeface(font);
        OK.setTypeface(font);
        MYINFORTEXT.setTypeface(font);


        user = new UserInfo(getIntent().getExtras().getString("My Account"));
        myInformation = new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");

        friendNum = 1;
        imageLayout = (GridLayout) findViewById(R.id.imageLayout);


        MYINFORMATION = (Button)findViewById(R.id.myInformationView);
        ADD = (Button)findViewById(R.id.ADD);
        INVITED = (Button)findViewById(R.id.beAdded);

        myInformation.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot.child(user.account).child("BEADDED").getValue().equals(Boolean.toString(false)))INVITED.setVisibility(View.GONE);
                while (!dataSnapshot.child(user.account).child("FRIEND").child(Integer.toString(friendNum)).getValue().equals("false")){
                    String myfriend = (String) dataSnapshot.child(user.account).child("FRIEND").child(Integer.toString(friendNum)).getValue();
                    v[friendNum-1] = new ImageView(Friendlist.this);
                    imageLayout.addView(v[friendNum-1]);
                    Picasso.with(Friendlist.this)
                            .load(Uri.parse((String) dataSnapshot.child(myfriend).child("PROFILE").getValue()))
                            .resize(150,150)
                            .into(v[friendNum-1]);

                    friendNum++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        }));


        ADD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                myInformation.addValueEventListener((new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.account).child("ADDFRIEND").getValue().equals(Boolean.toString(false))) {
                            Intent intent = new Intent(Friendlist.this, MakeFriend.class);
                            intent.putExtra("My Account", user.account);
                            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            return;
                        }
                        Intent intent = new Intent(Friendlist.this, Myfriend.class);
                        intent.putExtra("My Account", user.account);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                }));
            }
        });

        MYINFORMATION.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friendlist.this, UserInterface.class);
                intent.putExtra("My Account", user.account);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        INVITED.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friendlist.this, BeAddedFriend.class);
                intent.putExtra("My Account", user.account);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


    }
}

