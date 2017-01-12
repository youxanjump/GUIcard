package com.example.user.guicard;

import android.net.Uri;

public class UserInfo {
    public String account;
    public String password;
    public String name;
    public Uri profileUri;
    public int interes;

    public UserInfo(String account,String password,String name,Uri profileUri,int interes){
        this.account=account;
        this.password=password;
        this.name=name;
        this.profileUri=profileUri;
        this.interes=interes;
    }
    public UserInfo(String account,String password,String name){
        this.account=account;
        this.password=password;
        this.name=name;
    }
    public UserInfo(String account,String password,String name,int interes){
        this.account=account;
        this.password=password;
        this.name=name;
        this.interes=interes;
    }

    public UserInfo(String account,String name,Uri profileUri,int interes){
        this.account=account;
        this.name=name;
        this.profileUri=profileUri;
        this.interes=interes;
    }

    public UserInfo(String account){
        this.account=account;
    }

}
