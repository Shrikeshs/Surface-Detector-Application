package com.example.accelerationsample;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ZAxes.db";
    public static final String COL1 = "time";
    public static final String COL2 = "axis";
    public static final String TABLE_NAME = "ZA";
    ArrayList<Double> arrayList = new ArrayList<>();
    float diff=9;
    float res;
    int i;

     float  result=1;
    private Context context;


    public DBHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                        "( " + COL1 + " int," + COL2 + " float)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void insertData(int t, float z) {

        SQLiteDatabase dbe = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, t);
        contentValues.put(COL2, z);
        dbe.insert(TABLE_NAME, null, contentValues);
      /*  long count = DatabaseUtils.queryNumEntries(dbe, TABLE_NAME);
        Log.i("LL","Count="+count);*/
        /*if(count>10)*/

    }

    public void deleteData() {
        arrayList.clear();
        SQLiteDatabase dbe = this.getWritableDatabase();
        dbe.execSQL("DELETE FROM " + TABLE_NAME);
    }

   public void getData() {

        SQLiteDatabase dbe = this.getWritableDatabase();
        Cursor cursor=dbe.rawQuery("select avg(axis) from ZA  GROUP by(time)",null);
        Cursor cursorTime=dbe.rawQuery("select time from ZA group by(time)",null);
        Log.i("LLL","Cursor Count="+cursorTime.getCount());
       if(cursorTime.getCount()>58){
            dbe.execSQL("Delete from ZA");
            Log.i("PPP","Deleted=");
        }
        while(cursor.moveToNext()) {

        manipulateData(cursor);



       }


        }

    public void manipulateData(Cursor cursor){
        i=0;
        res= Math.abs(Float.parseFloat(cursor.getString(i))-diff);
        diff=Float.parseFloat(cursor.getString(i));
        if(res >=1.500) {
            Log.i("LL","Count="+res);
            Toast.makeText(context, " Detected= "+res, Toast.LENGTH_SHORT).show();
            res=0;


        }

    }
        }











