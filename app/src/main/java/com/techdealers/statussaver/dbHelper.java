package com.techdealers.statussaver;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private Context context;

    public dbHelper(Context context) {
        super(context, "saver", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists state(state varchar(20) not null)");
        db.execSQL("insert into state values ('not checked')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table if not exists state(state varchar(20) not null)");
    }

    public void changeState(String state){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("update state set state='"+state+"' where 1");
    }

    public String getState() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from state where 1",null);

        String state = "not checked";

        if (cursor.moveToNext()) {
            state = cursor.getString(0);
        }

        return state;
    }

}
