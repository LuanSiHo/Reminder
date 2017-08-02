package com.hosiluan.reminder.presenter.presenterAddNewTaskActivity;

import android.content.Context;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.model.modelAddNewTaskActivity.ModelAddNewTaskActivity;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.listener.ViewAddNewTaskActivityListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/18/2017.
 */

public class PresenterAddNewTaskActivity implements PresenterAddToListActivityListener {

    private Context mContext;
    private ViewAddNewTaskActivityListener viewAddNewTaskActivityListener;
    private ModelAddNewTaskActivity modelAddNewTaskActivity;


    public PresenterAddNewTaskActivity(Context mContext, ViewAddNewTaskActivityListener viewAddNewTaskActivityListener) {
        this.mContext = mContext;
        this.viewAddNewTaskActivityListener = viewAddNewTaskActivityListener;
        modelAddNewTaskActivity = new ModelAddNewTaskActivity(mContext, this);
    }


    public void initAddToListSpinner() {
        modelAddNewTaskActivity.initAddToListSpinner();
    }

    public void findTaskTypeIdByName(String taskTypeName){
        modelAddNewTaskActivity.findTaskTypeIdByName(taskTypeName);
    }

    public void addTaskItem(TaskItem taskItem){
        modelAddNewTaskActivity.addTaskItem(taskItem);
    }


    public void getTaskTypeById(int id){
       modelAddNewTaskActivity.getTaskTypeById(id);
    }

    @Override
    public void onGetTypeListResult(ArrayList<TaskType> taskTypes) {
        viewAddNewTaskActivityListener.onGetTypeListResult(taskTypes);
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        viewAddNewTaskActivityListener.onFindTaskTypeIdByNameResult(id);
    }

    @Override
    public void onGetTaskTypeByIdResult(TaskType taskType) {
        viewAddNewTaskActivityListener.onGetTaskTypeByIdResult(taskType);
    }

    public void updateTaskItem(TaskItem taskItem) {
        modelAddNewTaskActivity.updateTaskItem(taskItem);
    }
}
