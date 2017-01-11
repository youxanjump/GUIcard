package com.example.user.guicard;

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

public class UserInterface extends AppCompatActivity {

  private Firebase myInformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myInteres;
    private Button List;
    private UserInfo user;
    private String myAccount;
    private String[] interes = {"木吉他","演奏","貝斯","電吉他","演唱","鼓組","鍵盤","創作"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);

        List = (Button)findViewById(R.id.myInformationView);
        myProfile = (ImageView)findViewById(R.id.image);
        myName = (TextView)findViewById(R.id.NAME);
        myInteres = (TextView)findViewById(R.id.INTERES);

        findViewById(R.id.FUNCTION).setVisibility(View.GONE);
        findViewById(R.id.FUNCTION_TEXT).setVisibility(View.GONE);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sushi.ttf");
        myName.setTypeface(font);
        myInteres.setTypeface(font);
        myInformation =  new Firebase("https://guicard-de0f4.firebaseio.com/").child("USER");
        myAccount = getIntent().getExtras().getString("My Account");
        //Bundle the user's information
        myInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.child(myAccount).getValue(Map.class);
                user = new UserInfo(myAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTEREST")));
                //show your profile
                Picasso.with(UserInterface.this).load(user.profileUri).into(myProfile);
                myName.setText(user.name);

                while (user.interes > 0) {
                    int interesCount = 0;
                    int interesOfMine = 1;
                    if(user.interes==1){user.interes=0;interesCount++;}
                    while (user.interes > interesOfMine) {
                        interesOfMine = 2 * interesOfMine;
                        interesCount++;
                    }
                    user.interes = user.interes - interesOfMine/2;
                    myInteres.append(interes[interesCount-1]+" ");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        List.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInterface.this,Friendlist.class);
                intent.putExtra("My Account",user.account);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }
}


