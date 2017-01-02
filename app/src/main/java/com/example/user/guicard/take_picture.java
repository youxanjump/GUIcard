/*package com.example.user.guicard;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;



 *Created by USER on 2016/12/19.


public class take_picture extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_INTENT = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private final int CROP_PIC = 3;
    private Button log;
    private Button slectImage;
    private Button takePicture;
    private Button back;
    private ImageView imagePreview;

    private Firebase firebase;
    private StorageReference imaStorage;
    private ProgressDialog imgProgress;
    UserInfo user;
    Uri imageReg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);

        firebase = new Firebase("https://guicard-de0f4.firebaseio.com/");
        back = (Button) findViewById(R.id.back);
        log = (Button) findViewById(R.id.LOG);
        slectImage = (Button) findViewById(R.id.slect_image);
        takePicture = (Button) findViewById(R.id.take_image);
        imagePreview = (ImageView)findViewById(R.id.imagePreview);

        imaStorage = FirebaseStorage.getInstance().getReference();
        imgProgress = new ProgressDialog(this);

        user = new UserInfo(getIntent().getExtras().getString("ACCOUNT"), getIntent().getExtras().getString("PASSWORD"),
                getIntent().getExtras().getString("NAME"),null, getIntent().getExtras().getInt("INTEREST"));

        back.setOnClickListener(this);
        log.setOnClickListener(this);
        slectImage.setOnClickListener(this);
        takePicture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == back){
            Intent intent = new Intent(this,SignUp.class);
            intent.putExtra("ACCOUNT",user.account);
            intent.putExtra("PASSWORD",user.password);
            intent.putExtra("NAME",user.name);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

        if (v == log) {

            if (imageReg == null) {
                Toast.makeText(take_picture.this, "Please choose your PROFILE PICTURE", Toast.LENGTH_SHORT).show();return;
            }
            else {

                upLoadPic();

                firebase.child(user.account).child("PASSWORD").setValue(user.password);
                firebase.child(user.account).child("NAME").setValue(user.name);
                firebase.child(user.account).child("INTERES").setValue(Integer.toString(user.interes));
                firebase.child(user.account).child("GENDER").setValue(Integer.toString(user.gender));
                firebase.child(user.account).child("PROFILE").setValue(user.profileUri.toString());

                Toast.makeText(take_picture.this, "Sign Success!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(take_picture.this, UserInterface.class);
                intent.putExtra("MyAccount", user.account);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        }
        if (v == slectImage) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        }
        if (v == takePicture) {
            try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, "This device doesn't support the camera action!",Toast.LENGTH_SHORT).show();
        }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == GALLERY_INTENT || requestCode == CAMERA_REQUEST_CODE) {
                imageReg = data.getData();
                performCrop();
            }
            else if(requestCode == CROP_PIC){
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                imagePreview.setImageBitmap(thePic);

            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(imageReg, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("outputFormat","JPEG");
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            String localTempImgDir="Hello";

            String localTempImgFileName = System.currentTimeMillis()+".jpg";
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageReg);

            File f=new File(Environment.getExternalStorageDirectory() +"/"+localTempImgDir+"/"+localTempImgFileName);
            imageReg = Uri.fromFile(f);



            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadPic(){
        imgProgress.setMessage("Sign Up ...");
        imgProgress.show();

        StorageReference imagePath = imaStorage.child("Profiles").child(imageReg.getLastPathSegment());
        imagePath.putFile(imageReg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                user.profileUri = taskSnapshot.getDownloadUrl();
                imgProgress.dismiss();
            }
        });
    }

}**/
