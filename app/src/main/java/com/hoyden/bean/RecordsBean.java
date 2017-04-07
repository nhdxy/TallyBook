package com.hoyden.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhd on 2017/4/7.
 */

public class RecordsBean implements Parcelable {
    private long date;
    private String type;
    private String content;
    private String price;

    public RecordsBean() {
    }

    public RecordsBean(long date, String type, String content, String price) {
        this.date = date;
        this.type = type;
        this.content = content;
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeString(this.price);
    }

    protected RecordsBean(Parcel in) {
        this.date = in.readLong();
        this.type = in.readString();
        this.content = in.readString();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<RecordsBean> CREATOR = new Parcelable.Creator<RecordsBean>() {
        @Override
        public RecordsBean createFromParcel(Parcel source) {
            return new RecordsBean(source);
        }

        @Override
        public RecordsBean[] newArray(int size) {
            return new RecordsBean[size];
        }
    };
}
