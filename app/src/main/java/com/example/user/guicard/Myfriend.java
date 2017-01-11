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
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class Myfriend extends AppCompatActivity {

    private Firebase friendInformation;
    private Firebase myInformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myInteres;
    private TextView inviteText;
    private Button List;
    private Button Invite;
    private ProgressDialog imgProgress;
    private UserInfo myfriend;
    private UserInfo user;
    private String[] interes = {"木吉他","演奏","貝斯","電吉他","演唱","鼓組","鍵盤","創作"};

    private String myAccount;
    private String friendAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);

        Invite = (Button)findViewById(R.id.FUNCTION);
        List = (Button)findViewById(R.id.myInformationView);
        myProfile = (ImageView)findViewById(R.id.image);
        myName = (TextView)findViewById(R.id.NAME);
        myInteres = (TextView)findViewById(R.id.INTERES);
        inviteText = (TextView)findViewById(R.id.FUNCTION_TEXT);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sushi.ttf");
        myName.setTypeface(font);
        myInteres.setTypeface(font);
        inviteText.setText("Invite");

        user = new UserInfo(getIntent().getExtras().getString("My Account"));

        myInformation = new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER").child(user.account);
        friendInformation =  new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");

        imgProgress = new ProgressDialog(this);
        imgProgress.setMessage("Loading ...");
        imgProgress.show();
        //Bundle the user's information

        myInformation.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendAccount = dataSnapshot.child("ADDFRIEND").getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        }));


        friendInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = dataSnapshot.child(friendAccount).getValue(Map.class);
                myfriend = new UserInfo(friendAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTEREST")));

                if(!(map.get("BEADDED").equals(Boolean.toString(false)))) {
                    Invite.setVisibility(View.GONE);
                    inviteText.setVisibility(View.GONE);
                }

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
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myfriend.this,Friendlist.class);
                intent.putExtra("My Account",user.account);
                startActivity(intent);
            }
        });

        Invite.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                friendInformation.child(myfriend.account).child("BEADDED").setValue(user.account);
                Toast.makeText(Myfriend.this,"Send Invite!",Toast.LENGTH_SHORT).show();
                Invite.setVisibility(View.GONE);
                inviteText.setVisibility(View.GONE);
            }

        });
    }
}


