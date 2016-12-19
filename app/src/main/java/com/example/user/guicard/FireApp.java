package com.example.user.guicard;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by USER on 2016/12/15.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
