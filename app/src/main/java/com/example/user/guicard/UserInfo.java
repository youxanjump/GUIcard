package com.example.user.guicard;

import android.net.Uri;

/**
 * Created by USER on 2016/12/15.
 */

public class UserInfo {
    String account;
    String password;
    String name;
    Uri profileUri;
    int interes;
    int gender;

    public UserInfo(String account,String password,String name,Uri profileUri,int interes,int gender){
        this.account=account;
        this.password=password;
        this.name=name;
        this.profileUri=profileUri;
        this.interes=interes;
        this.gender=gender;
    }

    public UserInfo(String account,String password,String name,int interes,int gender){
        this.account=account;
        this.password=password;
        this.name=name;
        this.interes=interes;
        this.gender=gender;
    }
}
