package com.example.user.guicard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_INTENT = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private Firebase firebase;
    private Firebase firebaseAccount;
    private Firebase firebasePassword;
    private Firebase firebaseName;
    private Firebase firebaseProfile;
    private Firebase firebaseInteres;
    private Firebase firebaseGender;
    private Button log;
    private Button slectImage;
    private Button takePicture;
    private StorageReference imaStorage;
    private ProgressDialog imgProgress;
    private Uri profileUri;
    private EditText account;
    private EditText password;
    private EditText name;
    private RadioGroup genderChoose;
    private CheckBox a1;
    private CheckBox a2;
    private CheckBox a3;
    private CheckBox a4;
    private CheckBox a5;
    private CheckBox a6;
    private CheckBox a7;
    private CheckBox a8;
    private int gender;
    private int interest;
    private String sendAccount;
    private String sendPassword;
    private String sendName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebase=new Firebase("https://guicard-de0f4.firebaseio.com/");
        account = (EditText)findViewById(R.id.Account);
        password = (EditText)findViewById(R.id.Password);
        name = (EditText)findViewById(R.id.Name);
        log = (Button)findViewById(R.id.Log);
        slectImage = (Button)findViewById(R.id.slect_image);
        takePicture = (Button)findViewById(R.id.take_picture);
        imaStorage = FirebaseStorage.getInstance().getReference();
        imgProgress = new ProgressDialog(this);
        profileUri = null;
        gender = 0;
        genderChoose = (RadioGroup)findViewById(R.id.gender);
        a1 = (CheckBox)findViewById(R.id.a1);
        a2 = (CheckBox)findViewById(R.id.a2);
        a3 = (CheckBox)findViewById(R.id.a3);
        a4 = (CheckBox)findViewById(R.id.a4);
        a5 = (CheckBox)findViewById(R.id.a5);
        a6 = (CheckBox)findViewById(R.id.a6);
        a7 = (CheckBox)findViewById(R.id.a7);
        a8 = (CheckBox)findViewById(R.id.a8);
        interest = 0;

        genderChoose.check(R.id.man);//drfine
        log.setOnClickListener(this);
        slectImage.setOnClickListener(this);
        takePicture.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        if(v==log){

            switch (genderChoose.getCheckedRadioButtonId()){
                case R.id.man:gender=0;break;
                case R.id.woman:gender=1;break;
            }

            if(a1.isChecked())interest+=1;
            if(a2.isChecked())interest+=2;
            if(a3.isChecked())interest+=4;
            if(a4.isChecked())interest+=8;
            if(a5.isChecked())interest+=16;
            if(a6.isChecked())interest+=32;
            if(a7.isChecked())interest+=64;
            if(a8.isChecked())interest+=128;

            sendAccount=account.getText().toString();
            sendPassword=password.getText().toString();
            sendName=name.getText().toString();

            firebase.addValueEventListener(new ValueEventListener() {
                UserInfo user = new UserInfo(sendAccount,sendPassword,sendName,profileUri,interest,gender);
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(profileUri==null){
                        Toast.makeText(SignUp.this,"Please choose your PROFILE PICTURE",Toast.LENGTH_SHORT).show();
                    }

                   else if(!dataSnapshot.child(user.account).exists()){

                        firebaseAccount=firebase.child(user.account);
                        firebasePassword=firebaseAccount.child("PASSWORD");
                        firebasePassword.setValue(user.password);
                        firebaseName=firebaseAccount.child("NAME");
                        firebaseName.setValue(user.name);
                        firebaseProfile=firebaseAccount.child("PROFILE");
                        firebaseProfile.setValue(user.profileUri.toString());
                        firebaseInteres=firebaseAccount.child("INTERES");
                        firebaseInteres.setValue(Integer.toString(user.interes));
                        firebaseGender=firebaseAccount.child("GENDER");
                        firebaseGender.setValue(Integer.toString(user.gender));

                        Toast.makeText(SignUp.this,"Sign Success!",Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(SignUp.this,UserInterface.class);
                        intent.putExtra("MyAccount", user.account);
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                   else Toast.makeText(SignUp.this,"This account has already exists!",Toast.LENGTH_SHORT).show();
                 }

              @Override
             public void onCancelled(FirebaseError firebaseError) {

             }
        });
    }
        if(v==slectImage){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,GALLERY_INTENT);
        }
        if(v==takePicture){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == GALLERY_INTENT || requestCode == CAMERA_REQUEST_CODE) && resultCode == RESULT_OK){

            imgProgress.setMessage("Uploading...");
            imgProgress.show();
            Uri uri = data.getData();

            final StorageReference imagePath = imaStorage.child("Profiles").child(uri.getLastPathSegment());

            imagePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileUri = taskSnapshot.getDownloadUrl();
                    Toast.makeText(SignUp.this,"Upload Done!",Toast.LENGTH_SHORT).show();
                    imgProgress.dismiss();
                    slectImage.setEnabled(false);
                    takePicture.setEnabled(false);
                }
            });

        }
    }
}
