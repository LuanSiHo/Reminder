package com.hosiluan.reminder.Item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HoSiLuan on 7/4/2017.
 */

public class TaskType  implements Parcelable {
    private int mIcon;
    private String mName;
    private int mId;
    private int mAmountOfTask;

    public TaskType(){

    }

    public TaskType(String mName, int mId) {
        this.mName = mName;
        this.mId = mId;
    }

    public TaskType(String mName) {
        this.mName = mName;
    }

    public TaskType(int mIcon, String mName, int mAmountOfTask) {
        this.mIcon = mIcon;
        this.mName = mName;
        this.mAmountOfTask = mAmountOfTask;
    }

    public TaskType(int mIcon, String mName, int mId, int mAmountOfTask) {
        this.mIcon = mIcon;
        this.mName = mName;
        this.mId = mId;
        this.mAmountOfTask = mAmountOfTask;
    }



    protected TaskType(Parcel in) {
        mIcon = in.readInt();
        mName = in.readString();
        mId = in.readInt();
        mAmountOfTask = in.readInt();
    }



    public static final Creator<TaskType> CREATOR = new Creator<TaskType>() {
        @Override
        public TaskType createFromParcel(Parcel in) {
            return new TaskType(in);
        }

        @Override
        public TaskType[] newArray(int size) {
            return new TaskType[size];
        }
    };

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmAmountOfTask() {
        return mAmountOfTask;
    }

    public void setmAmountOfTask(int mAmountOfTask) {
        this.mAmountOfTask = mAmountOfTask;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIcon);
        parcel.writeString(mName);
        parcel.writeInt(mId);
        parcel.writeInt(mAmountOfTask);
    }
}
