package com.hosiluan.reminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HoSiLuan on 7/8/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "ReminderDB";
    public static String TB_TASK_TYPE = "TaskType";
    public static String TB_TASK = "Task";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    public static final String TASK_TYPE_ID = "id";
    public static final String TASK_TYPE_NAME = "name";


    private static String CREATE_TABLE_TASK_TYPE = "CREATE TABLE " + TB_TASK_TYPE + " ( " +
            TASK_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TASK_TYPE_NAME + " TEXT)";


    public static final String TASK_ID = "id";
    public static final String TASK_CONTENT = "content";
    public static final String TASK_DATETIME = "time";
    public static final String TASK_TYPE = "tasktype";
    public static final String TASK_COLOR = "taskcolor";
    public static final String TASK_REPEAT = "taskrepeat";

    private static String CREATE_TABLE_TASK = "CREATE TABLE " + TB_TASK + " (" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK_CONTENT + " TEXT, " +
            TASK_DATETIME + " TEXT , " +
            TASK_TYPE + " INTEGER , " +
            TASK_COLOR + " INTEGER , " +
            TASK_REPEAT + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK_TYPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        Log.d("hello", "create db successfull");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


}
