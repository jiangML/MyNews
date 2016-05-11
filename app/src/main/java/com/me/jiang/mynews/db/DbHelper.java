package com.me.jiang.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/10.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static  String DB_NAME="mynews.db";
    private static int  VERSION=1;

    private static final String TABLE_CHANNEL="create table if not exists channel ( _id integer primary key autoincrement  ,channel_id ,channel_name ) ";
    private static final String TABLE_NEWS="create table if not exists news (_id integer primary key autoincrement ,news_content,news_page,news_channel)";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CHANNEL);
        db.execSQL(TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
