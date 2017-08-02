package com.hosiluan.reminder.model.modelTaskTypeActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.database.DataSource;
import com.hosiluan.reminder.presenter.presenterTaskTypeActivity.PresenterTaskTypeActivityListener;

import java.util.ArrayList;
import java.util.logging.Level;

import static com.hosiluan.reminder.database.DBHelper.TASK_TYPE;
import static com.hosiluan.reminder.database.DBHelper.TB_TASK;

/**
 * Created by HoSiLuan on 7/25/2017.
 */

public class ModelTaskTypeActivity {

    Context mContext;
    DataSource mDataSource;
    PresenterTaskTypeActivityListener presenterTaskTypeActivityListener;

    public ModelTaskTypeActivity(Context mContext, PresenterTaskTypeActivityListener presenterTaskTypeActivityListener) {
        this.mContext = mContext;
        this.presenterTaskTypeActivityListener = presenterTaskTypeActivityListener;
        mDataSource = new DataSource(mContext);
    }

    public void loadTaskType() {
        ArrayList<TaskType> taskTypes = mDataSource.getTaskTypeList();
        presenterTaskTypeActivityListener.onGetTaskTypeListResult(taskTypes);
    }

    public void findTaskTypeByName(String name) {
        if (mDataSource.findTaskTypeByName(name)) {
            presenterTaskTypeActivityListener.onFindTaskTypeByNameFound(name);
        } else {
            presenterTaskTypeActivityListener.onFindTaskTypeByNameNotFound(name);
        }
    }

    public void addTaskType(TaskType taskType) {
        mDataSource.addTaskType(taskType);
    }


    public void findTaskTypeIdByName(String taskTypeName) {
        int id = mDataSource.findTaskTypeIdByName(taskTypeName);
        presenterTaskTypeActivityListener.onFindTaskTypeIdByNameResult(id);
    }

    public void updateTaskType(TaskType taskType){
        mDataSource.updateTaskType(taskType);
    }

    public void deleteTaskType(String taskTypeName) {
        mDataSource.deleteTaskType(new TaskType(taskTypeName));
    }

    public void deleteTaskItemByType(int taskType){
        mDataSource.deleteTaskItemByType(taskType);
    }

    public void getTaskListByType(int taskTypeId) {
        ArrayList<TaskItem> taskItems = mDataSource.getTaskListByType(taskTypeId);
        Log.d("hello",taskItems.size() + " task size");
        presenterTaskTypeActivityListener.onGetTaskListByTypeResult(taskItems);
    }
}
