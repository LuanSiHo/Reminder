package com.hosiluan.reminder.model.modelMainActivity;

import android.content.Context;
import android.util.Log;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.database.DataSource;
import com.hosiluan.reminder.presenter.presenterMainActivity.PresenterMainActivityListener;

import java.util.ArrayList;


/**
 * Created by HoSiLuan on 7/17/2017.
 */

public class ModelMainActivity {

    private Context mContext;
    private DataSource mDataSource;
    private PresenterMainActivityListener presenterMainActivityListener;

    public ModelMainActivity(PresenterMainActivityListener presenterMainActivityListener, Context context) {
        this.presenterMainActivityListener = presenterMainActivityListener;
        mContext = context;
        mDataSource = new DataSource(mContext);
    }

    public void initTaskTypeSpinner() {
        ArrayList<TaskType> taskTypes = mDataSource.getTaskTypeList();
        presenterMainActivityListener.onGetTypeListResult(taskTypes);
    }

    public void findTaskTypeByName(String taskTypeName) {
        ArrayList<TaskType> taskTypes = mDataSource.getTaskTypeList();

        boolean result = mDataSource.findTaskTypeByName(taskTypeName);
        if (result) {
            presenterMainActivityListener.onTaskTypeFound(taskTypeName);
        } else {
            presenterMainActivityListener.onTaskTypeNotFound(taskTypeName, taskTypes);
        }
    }

    public void addTaskType(TaskType taskType) {
        mDataSource.addTaskType(taskType);
    }


    public void loadTaskList() {
        ArrayList<TaskItem> taskItems = mDataSource.getAllTaskItem();
        presenterMainActivityListener.getTaskListResult(taskItems);

    }

    public void findTaskTypeIdByName(String taskTypeName) {

        Log.d("hello","null gi ma null " + taskTypeName);
        if (taskTypeName.equals("All Lists")) {
            loadTaskList();
        } else {
            int id = mDataSource.findTaskTypeIdByName(taskTypeName);
            presenterMainActivityListener.onFindTaskTypeIdByNameResult(id);
        }
    }

    public void findTaskListByType(int taskType) {
        ArrayList<TaskItem> taskItems = mDataSource.getTaskListByType(taskType);
        presenterMainActivityListener.onFindTaskListByTypeResult(taskItems);
    }

    public void deleteTaskItem(ArrayList<TaskItem> taskItems){
        for (int i = 0; i < taskItems.size(); i ++){
            mDataSource.deleteTaskItem(taskItems.get(i));
        }
    }

    public void findTaskItem(String taskItem) {
        ArrayList<TaskItem> taskItems = mDataSource.findTaskItem(taskItem);
        if (taskItems.size() > 0){
            presenterMainActivityListener.onFindTaskItemFound(taskItems);
        }else {
            presenterMainActivityListener.onFindTaskItemNotFound();
        }
    }
}

