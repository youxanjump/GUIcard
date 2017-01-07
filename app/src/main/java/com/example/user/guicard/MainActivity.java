package com.example.user.guicard;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        TextView GUI = (TextView) findViewById(R.id.GUI);
        TextView Card = (TextView) findViewById(R.id.Card);

        //修改字體
        GUI.setTypeface(font);
        Card.setTypeface(font);

        Thread loading = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(),LogMain.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        loading.start();
    }
}
