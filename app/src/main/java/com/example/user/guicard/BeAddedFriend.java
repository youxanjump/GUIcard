package com.example.user.guicard;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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

/**
 * Created by user on 2017/1/11.
 */

public class BeAddedFriend extends AppCompatActivity {
    private Firebase userInformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myInteres;
    private TextView acceptText;
    private Button List;
    private Button accept;
    private UserInfo addedMe;
    private String myAccount;
    private String addedMeAccount;
    private String[] interes = {"木吉他","演奏","貝斯","電吉他","演唱","鼓組","鍵盤","創作"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);

        List = (Button)findViewById(R.id.MYINFOR);
        accept = (Button)findViewById(R.id.FUNCTION);
        myProfile = (ImageView)findViewById(R.id.image);
        myName = (TextView)findViewById(R.id.NAME);
        myInteres = (TextView)findViewById(R.id.INTERES);
        acceptText = (TextView)findViewById(R.id.FUNCTION_TEXT);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sushi.ttf");
        myName.setTypeface(font);
        myInteres.setTypeface(font);
        acceptText.setText("Accept");

        myAccount = getIntent().getExtras().getString("My Account");
        userInformation =  new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");
        userInformation.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addedMeAccount = dataSnapshot.child(myAccount).child("BEADDED").getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        }));

        //Bundle the user's information
        userInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.child(addedMeAccount).getValue(Map.class);
                addedMe = new UserInfo(addedMeAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTEREST")));
                //show your profile
                Picasso.with(BeAddedFriend.this).load(addedMe.profileUri).into(myProfile);
                myName.setText(addedMe.name);

                while (addedMe.interes > 0) {
                    int interesCount = 0;
                    int interesOfMine = 1;
                    if(addedMe.interes==1){addedMe.interes=0;interesCount++;}
                    while (addedMe.interes > interesOfMine) {
                        interesOfMine = 2 * interesOfMine;
                        interesCount++;
                    }
                    addedMe.interes = addedMe.interes - interesOfMine/2;
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
                Intent intent = new Intent(BeAddedFriend.this,Friendlist.class);
                intent.putExtra("My Account",myAccount);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        accept.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                userInformation.addValueEventListener((new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int friendNum = 1;
                        userInformation.child(myAccount).child("BEADDED").setValue(Boolean.toString(false));
                        while (!(dataSnapshot.child(myAccount).child("FRIEND").child(Integer.toString(friendNum)).getValue()).equals(Boolean.toString(false)))friendNum++;
                        userInformation.child(myAccount).child("FRIEND").child(Integer.toString(friendNum)).setValue(addedMeAccount);
                        userInformation.child(myAccount).child("FRIEND").child(Integer.toString(friendNum+1)).setValue(Boolean.toString(false));

                        friendNum = 1;
                        userInformation.child(addedMeAccount).child("ADDFRIEND").setValue(Boolean.toString(false));
                        while (!(dataSnapshot.child(addedMeAccount).child("FRIEND").child(Integer.toString(friendNum)).getValue().equals(Boolean.toString(false))))friendNum++;
                        userInformation.child(addedMeAccount).child("FRIEND").child(Integer.toString(friendNum)).setValue(myAccount);
                        userInformation.child(addedMeAccount).child("FRIEND").child(Integer.toString(friendNum+1)).setValue(Boolean.toString(false));

                        Intent intent = new Intent(BeAddedFriend.this,Friendlist.class);
                        intent.putExtra("My Account",myAccount);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                }));

            }
        });
    }

}
