package com.hoyden.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoyden.bean.RecordsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhd on 2017/4/7.
 */

public class DbTally {
    private SQLiteDatabase db;

    private DbTally() {
        db = DbHelper.getInstance().getReadableDatabase();
    }

    public synchronized static DbTally getInstance() {
        return new DbTally();
    }

    public synchronized void insert(RecordsBean bean) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("date", bean.getDate());
        values.put("type", bean.getType());
        values.put("content", bean.getContent());
        values.put("price", bean.getPrice());
        db.insert("tb_tally", null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public synchronized void delete(String date) {
        db.delete("tb_tally", "date=?", new String[]{date});
        db.close();
    }

    public List<RecordsBean> getAll() {
        List<RecordsBean> mDatas = new ArrayList<>();
        Cursor cursor = db.query("tb_tally", null, null, null, null, null, "date DESC");
        while (cursor.moveToNext()) {
            RecordsBean bean = new RecordsBean();
            bean.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            bean.setPrice(cursor.getFloat(cursor.getColumnIndex("price")) + "");
            mDatas.add(bean);
        }
        cursor.close();
        db.close();
        return mDatas;
    }

    public List<RecordsBean> getAll(String orderBy) {
        List<RecordsBean> mDatas = new ArrayList<>();
        Cursor cursor = db.query("tb_tally", null, null, null, null, null, orderBy + " DESC");
        while (cursor.moveToNext()) {
            RecordsBean bean = new RecordsBean();
            bean.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            bean.setPrice(cursor.getFloat(cursor.getColumnIndex("price")) + "");
            mDatas.add(bean);
        }
        cursor.close();
        db.close();
        return mDatas;
    }
}
