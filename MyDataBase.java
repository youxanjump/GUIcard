package com.example.user.guicard_sqlite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by USER on 2016/12/6.
 */

public class MyDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_TABLE="UserInformation";
    public static final String ID="_id";
    public static final String ACCOUNT="ACCOUNT";
    public static final String PASSWORD="PASSWORD";
    public static final String NAME="NAME";
    public static final String INTERES="INTERES";
    public static final String GENDER="GENDER";


    public final String DB_CREATE_TABLE
            = "CREATE TABLE  " + DATABASE_TABLE + " ( " +
             ID + " INTEGER PRIMARY KEY," +
             ACCOUNT + " TEXT UNIQUE," +
             PASSWORD + "  TEXT NOT NULL," +
             NAME +" TEXT NOT NULL," +
             INTERES + "   INTEGER NOT NULL," +
             GENDER + "   INTEGER NOT NULL);";
    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {super(context,name,factory,version);}

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF TABLE EXISTS "+DATABASE_TABLE);
            onCreate(db);
    }

    public void add(SQLiteDatabase db,UserInfor user){
            ContentValues cv = new ContentValues();
            cv.put(NAME,user.name);
            cv.put(ACCOUNT,user.account);
            cv.put(PASSWORD,user.password);
            cv.put(ID,user.id);
            cv.put(INTERES,user.interes);
            cv.put(GENDER,user.gender);
            long adid = db.insert(DATABASE_TABLE,null,cv);
            Log.d("ADD", adid+"");
    }

    public void show(TextView result){

        Cursor cursor = getCursor();
        StringBuilder resultData = new StringBuilder("RESULT: \n");

        while(cursor.moveToNext()){
            //int id = cursor.getInt(0);
            String account = cursor.getString(1);
            String password = cursor.getString(2);
            String name = cursor.getString(3);
            int interes = cursor.getInt(4);
            int gender = cursor.getInt(5);

            resultData.append(account).append(": ");
            resultData.append(password).append(", ");
            resultData.append(name).append(", ");
            resultData.append(interes).append(", ");
            resultData.append(gender).append(", ");
            resultData.append("\n");
        }
        result.setText(resultData);
    }

    @SuppressWarnings("deprecation")
    private Cursor getCursor(){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID,ACCOUNT,PASSWORD, NAME, INTERES, GENDER};
        Cursor cursor = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
        return cursor;

    }


}
