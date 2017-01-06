package com.example.user.guicard;

//登入畫面SignIn SignUp
import android.view.View;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class LogMain extends AppCompatActivity implements View.OnClickListener {

    private Button In;
    private Button Up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Shine.ttf");
        TextView SignIn = (TextView) findViewById(R.id.SIGNIN);
        TextView SignUp = (TextView) findViewById(R.id.SIGNUP);
        TextView SignIn1 = (TextView) findViewById(R.id.SIGNIN1);
        TextView SignUp1 = (TextView) findViewById(R.id.SIGNUP1);

        //修改字體Textview
        SignIn.setTypeface(font1);
        SignUp.setTypeface(font1);
        SignIn1.setTypeface(font1);
        SignUp1.setTypeface(font1);


        In = (Button) findViewById(R.id.In);
        Up = (Button) findViewById(R.id.Up);
        In.setOnClickListener(this);
        Up.setOnClickListener(this);

    }

        @Override
        public void onClick(View v){
            if (v == Up) startActivity(new Intent(this, Log.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            else startActivity(new Intent(this, LogInview.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
}

