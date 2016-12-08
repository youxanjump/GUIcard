package com.example.user.guicard_sqlite_database;

/**
 * Created by USER on 2016/12/9.
 */

public class UserInfor {

    int id;
    String account;
    String password;
    String name;
    int interes;
    int gender;

    public UserInfor(int id,String account,String password,String name,int interes,int gender){
        this.id=id;
        this.account=account;
        this.password=password;
        this.name=name;
        this.interes=interes;
        this.gender=gender;
    }
}
