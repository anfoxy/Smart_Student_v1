package com.example.myapplication1.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(@Nullable Context context) {
        super(context, MyConstants.DB_NAME,null, MyConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyConstants.TABLE_STRUCTURE_QUE);
        db.execSQL(MyConstants.TABLE_STRUCTURE_SUB);
        db.execSQL(MyConstants.TABLE_STRUCTURE_TODAY);
        db.execSQL(MyConstants.TABLE_STRUCTURE_TIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MyConstants.DROP_TABLE_QUE);
        db.execSQL(MyConstants.DROP_TABLE_SUB);
        db.execSQL(MyConstants.DROP_TABLE_TODAY);
        db.execSQL(MyConstants.DROP_TABLE_TIME);
        onCreate(db);

    }
}