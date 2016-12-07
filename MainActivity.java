package com.example.user.guicard_sqlite_database;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String DATABASE_NAME="UserInformation";
    //public TextView result =(TextView)findViewById(R.id.result);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDataBase myDataBase = new MyDataBase(this,DATABASE_NAME,null,3);
        SQLiteDatabase db = myDataBase.getWritableDatabase();

        myDataBase.add(db,666,"U10316033","Youxan","Youxan",32,1);
        //myDataBase.show(result);
    }
}
