package com.hosiluan.reminder.Item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by HoSiLuan on 7/7/2017.
 */

public class TaskItem  implements Parcelable{

    private String mTaskText;
    private int mTaskIconColor;
    private Date mTaskDate;
    private int mId;
    private int mTaskType;
    private String mRepeat;


    public TaskItem(String mTaskText, Date mTaskDate) {
        this.mTaskText = mTaskText;
        this.mTaskDate = mTaskDate;
    }

    public TaskItem(String mTaskText, Date mTaskDate, int mId) {
        this.mTaskText = mTaskText;
        this.mTaskDate = mTaskDate;
        this.mId = mId;
    }

    public TaskItem(String mTaskText, Date mTaskDate, int mId, int mTaskType) {
        this.mTaskText = mTaskText;
        this.mTaskDate = mTaskDate;
        this.mId = mId;
        this.mTaskType = mTaskType;
    }

    public TaskItem(String mTaskText, Date mTaskDate, int mId, int mTaskType, int mTaskIconColor ) {
        this.mTaskText = mTaskText;
        this.mTaskIconColor = mTaskIconColor;
        this.mTaskDate = mTaskDate;
        this.mId = mId;
        this.mTaskType = mTaskType;
    }

    public TaskItem(String mTaskText,  Date mTaskDate, int mId, int mTaskType, int mTaskIconColor, String mRepeat) {
        this.mTaskText = mTaskText;
        this.mTaskIconColor = mTaskIconColor;
        this.mTaskDate = mTaskDate;
        this.mId = mId;
        this.mTaskType = mTaskType;
        this.mRepeat = mRepeat;
    }

    public TaskItem(){

    }
    public String getmRepeat() {
        return mRepeat;
    }

    public void setmRepeat(String mRepeat) {
        this.mRepeat = mRepeat;
    }
    protected TaskItem(Parcel in) {
        mTaskText = in.readString();
        mTaskIconColor = in.readInt();
        mId = in.readInt();
        mTaskType = in.readInt();
    }

    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel in) {
            return new TaskItem(in);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };

    public int getmTaskType() {
        return mTaskType;
    }

    public void setmTaskType(int mTaskType) {
        this.mTaskType = mTaskType;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTaskText() {
        return mTaskText;
    }

    public void setmTaskText(String mTaskText) {
        this.mTaskText = mTaskText;
    }

    public int getmTaskIconColor() {
        return mTaskIconColor;
    }

    public void setmTaskIconColor(int mTaskIconColor) {
        this.mTaskIconColor = mTaskIconColor;
    }

    public Date getmTaskDate() {
        return mTaskDate;
    }

    public void setmTaskDate(Date mTaskDate) {
        this.mTaskDate = mTaskDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTaskText);
        parcel.writeInt(mTaskIconColor);
        parcel.writeInt(mId);
        parcel.writeInt(mTaskType);
    }
}
