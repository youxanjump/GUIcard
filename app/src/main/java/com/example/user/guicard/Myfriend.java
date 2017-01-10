package com.example.user.guicard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class Myfriend extends AppCompatActivity {

    private Firebase friendInformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myInteres;
    private Button List;
    private ProgressDialog imgProgress;
    private UserInfo myfriend;
    private String friendAccount;
    private String[] interes = {"木吉他","演奏","貝斯","電吉他","演唱","鼓組","鍵盤","創作"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);

        List = (Button)findViewById(R.id.ok);
        myProfile = (ImageView)findViewById(R.id.image);
        myName = (TextView)findViewById(R.id.NAME);
        myInteres = (TextView)findViewById(R.id.INTERES);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sushi.ttf");
        myName.setTypeface(font);
        myInteres.setTypeface(font);

        friendInformation =  new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");
        friendAccount = getIntent().getExtras().getString("Friend");
        imgProgress = new ProgressDialog(this);
        imgProgress.setMessage("Loading ...");
        imgProgress.show();
        //Bundle the user's information
        friendInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.child(friendAccount).getValue(Map.class);
                myfriend = new UserInfo(friendAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTEREST")));

                //show your profile
                Picasso.with(Myfriend.this).load(myfriend.profileUri).into(myProfile);
                imgProgress.dismiss();
                myName.setText(myfriend.name);

                while (myfriend.interes > 0) {
                    int interesCount = 0;
                    int interesOfMine = 1;
                    if(myfriend.interes==1){myfriend.interes=0;interesCount++;}
                    while (myfriend.interes > interesOfMine) {
                        interesOfMine = 2 * interesOfMine;
                        interesCount++;
                    }
                    myfriend.interes = myfriend.interes - interesOfMine/2;
                    myInteres.append(interes[interesCount-1]+" ");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        List.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            }
        });
    }
}


