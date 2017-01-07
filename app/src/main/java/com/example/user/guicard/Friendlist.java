package com.example.user.guicard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Friendlist extends Activity {
    private Button ADD;
    private Typeface font;
    private TextView title;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);

        font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        title = (TextView)findViewById(R.id.title);
        title.setTypeface(font);
        user = new UserInfo(getIntent().getExtras().getString("My Account"));

        ADD = (Button)findViewById(R.id.ok);

        ADD.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Friendlist.this,MakeFriend.class);
                intent.putExtra("My Account",user.account);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }
}

