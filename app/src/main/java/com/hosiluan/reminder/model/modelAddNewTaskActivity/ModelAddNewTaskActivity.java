package com.hosiluan.reminder.model.modelAddNewTaskActivity;

import android.content.Context;
import android.util.Log;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.database.DataSource;
import com.hosiluan.reminder.presenter.presenterAddNewTaskActivity.PresenterAddToListActivityListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/18/2017.
 */

public class ModelAddNewTaskActivity {

    private DataSource mDataSource;
    private Context mContext;
    private PresenterAddToListActivityListener presenterAddToListActivityListener;

    public ModelAddNewTaskActivity(Context mContext, PresenterAddToListActivityListener presenterAddToListActivityListener) {
        this.mContext = mContext;
        this.presenterAddToListActivityListener = presenterAddToListActivityListener;
        mDataSource = new DataSource(mContext);
    }


    public void initAddToListSpinner() {
        ArrayList<TaskType> taskTypes;
        taskTypes = mDataSource.getTaskTypeList();
        Log.d("hello","task type size "  + taskTypes.size());
        presenterAddToListActivityListener.onGetTypeListResult(taskTypes);
    }

    public void findTaskTypeIdByName(String taskTypeName) {
        int id = mDataSource.findTaskTypeIdByName(taskTypeName);
        Log.d("hai",taskTypeName + " " + id);
        presenterAddToListActivityListener.onFindTaskTypeIdByNameResult(id);
    }

    public void getTaskTypeById(int id){
        TaskType taskType = mDataSource.getTaskTypeById(id);

        presenterAddToListActivityListener.onGetTaskTypeByIdResult(taskType);
    }
    public void addTaskItem(TaskItem taskItem) {
        mDataSource.addTaskItem(taskItem);
    }

    public void updateTaskItem(TaskItem taskItem) {
        mDataSource.updateTaskItem(taskItem);
    }
}
