package com.example.user.guicard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Map;

public class UserInterface extends AppCompatActivity {

    private Firebase myImformation;

    private ImageView myProfile;
    private TextView myName;
    private TextView myGender;
    private TextView myInteres;

    private UserInfo user;
    private String myAccount;
    private String[] interes = {"木吉他","電吉他","貝斯","鍵盤","鼓組","演唱","演奏","創作"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        myProfile = (ImageView)findViewById(R.id.profile);
        myName = (TextView)findViewById(R.id.NAME);
        myGender = (TextView)findViewById(R.id.GENDER);
        myInteres = (TextView)findViewById(R.id.INTERES);

        myImformation =  new Firebase("https://guicard-de0f4.firebaseio.com/");
        myAccount = getIntent().getExtras().getString("MyAccount");
        //Bundle the user's information
        myImformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.child(myAccount).getValue(Map.class);
                user = new UserInfo(myAccount, map.get("PASSWORD"), map.get("NAME"), Uri.parse(map.get("PROFILE")), Integer.parseInt(map.get("INTERES")), Integer.parseInt(map.get("GENDER")));

                //show your profile
                Picasso.with(UserInterface.this).load(user.profileUri).into(myProfile);
                myName.setText(user.name);

                switch (user.gender) {
                    case 0:
                        myGender.setText("MAN");
                        break;
                    case 1:
                        myGender.setText("WOMAN");
                        break;
                }

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

    }
}
