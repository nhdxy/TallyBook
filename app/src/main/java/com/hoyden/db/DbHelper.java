package com.hoyden.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hoyden.tallybook.MyApplication;

/**
 * Created by nhd on 2017/4/7.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper instance;
    public DbHelper(Context context) {
        super(context, "tally.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_tally (id INTEGER PRIMARY KEY AUTOINCREMENT,date long,type text,content text,price float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_tally");
        onCreate(db);
    }

    public synchronized static DbHelper getInstance(){
        if (null == instance) {
            instance = new DbHelper(MyApplication.getInstance());
        }
        return instance;
    }
}
