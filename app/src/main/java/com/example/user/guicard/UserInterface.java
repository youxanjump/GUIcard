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

  private Firebase myImformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myInteres;
    private Button List;

    private UserInfo user;
    private String myAccount;
    private String[] interes = {"木吉他","電吉他","貝斯","鍵盤","鼓組","演唱","演奏","創作"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);

        List = (Button)findViewById(R.id.ok);
        myProfile = (ImageView)findViewById(R.id.image);
        myName = (TextView)findViewById(R.id.NAME);
        myInteres = (TextView)findViewById(R.id.INTERES);

        Typeface font = Typeface.createFromAsset(getAssets(), "Sushi/surf.ttf");
        myName.setTypeface(font);
        myInteres.setTypeface(font);

        myImformation =  new Firebase("https://guicard-de0f4.firebaseio.com/");
        myAccount = getIntent().getExtras().getString("MyAccount");
        //Bundle the user's information
        myImformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.child(myAccount).getValue(Map.class);
                user = new UserInfo(myAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTERES")));

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
                startActivity(intent);
            }
        });
    }
}


