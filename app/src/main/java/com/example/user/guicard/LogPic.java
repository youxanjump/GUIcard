package com.example.user.guicard;

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
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class LogPic extends AppCompatActivity implements View.OnClickListener{

    private static final int GALLERY_INTENT = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private final int CROP_PIC = 3;
    private Button Pic;
    private Button Alb;
    private Button back;
    private Button OK;
    private ImageView imagePreview;

    private Firebase firebase;
    private StorageReference imaStorage;
    private ProgressDialog imgProgress;
    UserInfo user;
    Uri imageReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_pic);

        TextView Back = (TextView) findViewById(R.id.Back);
        TextView Next = (TextView) findViewById(R.id.Next);
        Pic = (Button) findViewById(R.id.Picture);
        Alb = (Button) findViewById(R.id.Album);
        back = (Button) findViewById(R.id.nextB1);
        OK = (Button) findViewById(R.id.back);
        imagePreview = (ImageView)findViewById(R.id.image);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sushi.ttf");
        Pic.setTypeface(font);
        Alb.setTypeface(font);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        Back.setTypeface(font1);
        Next.setTypeface(font1);

        firebase = new Firebase("https://guicard-de0f4.firebaseio.com/");
        imaStorage = FirebaseStorage.getInstance().getReference();
        imgProgress = new ProgressDialog(this);

        user = new UserInfo(getIntent().getExtras().getString("ACCOUNT"), getIntent().getExtras().getString("PASSWORD"),
                getIntent().getExtras().getString("NAME"),null, getIntent().getExtras().getInt("INTEREST"));

        back.setOnClickListener(this);
        OK.setOnClickListener(this);
        Pic.setOnClickListener(this);
        Alb.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        if(view == back){
            Intent intent = new Intent(this,Logview.class);
            intent.putExtra("ACCOUNT",user.account);
            intent.putExtra("PASSWORD",user.password);
            intent.putExtra("NAME",user.name);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        if(view == OK){
            if(imageReg == null){
                Toast.makeText(LogPic.this,"Please choose your PROFILE PICTURE", Toast.LENGTH_SHORT).show();
                return;
            }

            upLoadPic();

            firebase.child(user.account).child("NAME").setValue(user.name);
            firebase.child(user.account).child("PASSWORD").setValue(user.password);
            firebase.child(user.account).child("INTEREST").setValue(Integer.toString(user.interes));
            firebase.child(user.account).child("PROFILE").setValue(user.profileUri.toString());

            Toast.makeText(LogPic.this,"Sign Up Success",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogPic.this,LogupSuccess.class);
            intent.putExtra("My Account",user.account);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        //從相簿中挑照片
        if(view == Alb){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,GALLERY_INTENT);
        }
        //開啟內建相機拍照
        if(view == Pic){
            try{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } catch (ActivityNotFoundException anfe) {
                Toast.makeText(this, "This device doesn't support the camera action!",Toast.LENGTH_SHORT).show();
            }
        }
    }



    //可以裁切圖片大小
    private void Crop(){
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageReg, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("outputFormat", "JPEG");
            cropIntent.putExtra("return_data", true);

            String localTempImgDir = "Hello";
            String localTempImgFileName = System.currentTimeMillis() + ".jpg";
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageReg);

            File f = new File(Environment.getExternalStorageDirectory() + "/" + localTempImgDir + "/" + localTempImgFileName);
            imageReg = Uri.fromFile(f);

            startActivityForResult(cropIntent,CROP_PIC);

        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, "This Device doesn't support the crop action!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == GALLERY_INTENT || requestCode == CAMERA_REQUEST_CODE) {
                imageReg = data.getData();
                Crop();
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
}
