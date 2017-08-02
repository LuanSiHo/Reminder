package com.hosiluan.reminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;

import java.util.ArrayList;
import java.util.Date;

import static com.hosiluan.reminder.database.DBHelper.TASK_COLOR;
import static com.hosiluan.reminder.database.DBHelper.TASK_CONTENT;
import static com.hosiluan.reminder.database.DBHelper.TASK_DATETIME;
import static com.hosiluan.reminder.database.DBHelper.TASK_ID;
import static com.hosiluan.reminder.database.DBHelper.TASK_REPEAT;
import static com.hosiluan.reminder.database.DBHelper.TASK_TYPE;
import static com.hosiluan.reminder.database.DBHelper.TASK_TYPE_ID;
import static com.hosiluan.reminder.database.DBHelper.TASK_TYPE_NAME;
import static com.hosiluan.reminder.database.DBHelper.TB_TASK;
import static com.hosiluan.reminder.database.DBHelper.TB_TASK_TYPE;


/**
 * Created by HoSiLuan on 7/8/2017.
 */

public class DataSource {

    private Context mContext;
    private DBHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
    }

    public void addTaskType(TaskType taskType) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TYPE_NAME, taskType.getmName());
        database.insert(TB_TASK_TYPE, null, contentValues);
        database.close();
    }

    public void addTaskItem(TaskItem taskItem) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_CONTENT, taskItem.getmTaskText());
        contentValues.put(TASK_DATETIME, String.valueOf(taskItem.getmTaskDate().getTime()));
        contentValues.put(TASK_TYPE, taskItem.getmTaskType());
        contentValues.put(TASK_COLOR,taskItem.getmTaskIconColor());
        contentValues.put(TASK_REPEAT,taskItem.getmRepeat());
        database.insert(TB_TASK, null, contentValues);
        database.close();
    }

    public void updateTaskItem(TaskItem taskItem) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_CONTENT,taskItem.getmTaskText());
        contentValues.put(TASK_DATETIME,String.valueOf(taskItem.getmTaskDate().getTime()));
        contentValues.put(TASK_TYPE,taskItem.getmTaskType());
        contentValues.put(TASK_REPEAT,taskItem.getmRepeat());
        database.update(TB_TASK,contentValues,TASK_ID + " = ?", new String[]{taskItem.getmId() + ""});
        database.close();
    }

    public void updateTaskType(TaskType taskType){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TYPE_NAME,taskType.getmName());
        database.update(TB_TASK_TYPE,contentValues,TASK_TYPE_ID + " = ?",new String[]{taskType.getmId() + ""});
        database.close();
    }

    public void deleteTaskType(TaskType taskType) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        database.delete(TB_TASK_TYPE, TASK_TYPE_NAME + " = ?", new String[]{taskType.getmName()});
        database.close();
    }
    public void deleteTaskItemByType(int taskType){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        database.delete(TB_TASK,TASK_TYPE + " = ?", new String[]{taskType + ""});
        database.close();
    }

    public void deleteTaskItem(TaskItem taskItem){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        database.delete(TB_TASK,TASK_ID + " = ? ", new String[]{taskItem.getmId() + ""});
    }


    //t moi sua o day nay
    public TaskType getTaskTypeById(int id) {
        TaskType taskType = new TaskType();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from TaskType where id = ?", new String[]{id + ""});
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            taskType.setmName(cursor.getString(1));
            taskType.setmId(cursor.getInt(0));
        }else {
            taskType.setmName("Default");
            taskType.setmId(-1);
        }
        cursor.close();
        return taskType;
    }

    public ArrayList<TaskType> getTaskTypeList() {
        ArrayList<TaskType> taskTypes = new ArrayList<>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from TaskType", new String[]{});
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            taskTypes.add(new TaskType(cursor.getString(1),cursor.getInt(0)));
            cursor.moveToNext();
        }
        cursor.close();
        return taskTypes;
    }

    public ArrayList<TaskItem> getAllTaskItem() {
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TB_TASK, new String[]{});
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            taskItems.add(new TaskItem(cursor.getString(1), new Date(Long.parseLong(cursor.getString(2))), cursor.getInt(0),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            cursor.moveToNext();
        }
        cursor.close();
        return taskItems;
    }

    public ArrayList<TaskItem> getTaskListByType(int taskType) {
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Log.d("hello","id " +taskType);
        Cursor cursor = database.rawQuery("select * from " + TB_TASK + " where " + TASK_TYPE + " = ?", new String[]{taskType + " "});
        cursor.moveToFirst();
        Log.d("hello","count " + cursor.getCount());
        for (int i = 0; i < cursor.getCount(); i++) {
            taskItems.add(new TaskItem(cursor.getString(1), new Date(Long.parseLong(cursor.getString(2))), cursor.getInt(0),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            cursor.moveToNext();
        }

        cursor.close();
        return taskItems;
    }

    public ArrayList<TaskItem> findTaskItem(String taskItem){
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from "  + TB_TASK + " where " + TASK_CONTENT + " like ?",
                new String[]{ "%" +taskItem + "%"});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i =0; i < cursor.getCount(); i++){
                taskItems.add(new TaskItem(cursor.getString(1), new Date(Long.parseLong(cursor.getString(2))), cursor.getInt(0),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
                cursor.moveToNext();            }
        }
        cursor.close();
        return taskItems;
    }

    public int findTaskTypeIdByName(String taskTypeName) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TB_TASK_TYPE + " where " + TASK_TYPE_NAME + " = ?", new String[]{taskTypeName + ""});
        int id;
        if (cursor.getCount() == 0) {
            return -1;
        } else {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }

    public boolean findTaskTypeByName(String taskTypeName) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TB_TASK_TYPE + " where " + TASK_TYPE_NAME + " = ?", new String[]{taskTypeName});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }


}
