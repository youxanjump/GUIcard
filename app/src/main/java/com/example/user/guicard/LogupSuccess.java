package com.example.user.guicard;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.graphics.Typeface;
import android.widget.TextView;

public class LogupSuccess extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logup_success);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/surf.ttf");
        TextView GUI = (TextView) findViewById(R.id.GUI);
        TextView Card = (TextView) findViewById(R.id.Card);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Shine.ttf");
        TextView WT = (TextView)findViewById(R.id.WT);
        TextView ES = (TextView)findViewById(R.id.ES);
        TextView YM = (TextView)findViewById(R.id.YM);


        //修改字體
        GUI.setTypeface(font);
        Card.setTypeface(font);
        WT.setTypeface(font1);
        ES.setTypeface(font1);
        YM.setTypeface(font1);


        ImageView Fire = (ImageView)findViewById(R.id.Fire);
        ImageView Fire1 = (ImageView)findViewById(R.id.Fire1);

        //設定imageView的圖片來源
        Fire.setImageResource(R.drawable.firework);
        Fire1.setImageResource(R.drawable.firework);

        Animation Am = new AlphaAnimation(0,1);//動畫設定（透明度）
        Am.setDuration(2000);//動畫開始到結束的時間(1000 = 1秒)
        Am.setRepeatCount(-1);//動畫重複次數(-1表示重複)

        Animation Am2 = new ScaleAnimation(1,5,1,5);//動畫設定（比例）
        Am.setDuration(2000);//動畫開始到結束的時間(1000 = 1秒)
        Am.setRepeatCount(-1);//動畫重複次數(-1表示重複)
        //(動畫集合)
        AnimationSet am = new AnimationSet(false);
        am.addAnimation(Am);
        am.addAnimation(Am2);
        //配置動畫
        Fire.startAnimation(Am);
        Fire1.startAnimation(Am);
        Am.startNow();//啟動動畫
    }
}
