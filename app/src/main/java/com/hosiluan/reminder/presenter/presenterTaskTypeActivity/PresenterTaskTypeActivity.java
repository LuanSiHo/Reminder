package com.hosiluan.reminder.presenter.presenterTaskTypeActivity;

import android.content.Context;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.model.modelTaskTypeActivity.ModelTaskTypeActivity;
import com.hosiluan.reminder.view.viewTaskTypeActivity.ViewTaskTypeActivityListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/25/2017.
 */

public class PresenterTaskTypeActivity implements PresenterTaskTypeActivityListener {

    private ModelTaskTypeActivity modelTaskTypeActivity;
    private ViewTaskTypeActivityListener viewTaskTypeActivityListener;

    private Context mContext;


    public PresenterTaskTypeActivity(ViewTaskTypeActivityListener viewTaskTypeActivityListener, Context mContext) {
        this.viewTaskTypeActivityListener = viewTaskTypeActivityListener;
        this.mContext = mContext;
        modelTaskTypeActivity = new ModelTaskTypeActivity(mContext, this);
    }

    public void loadTaskType() {
        modelTaskTypeActivity.loadTaskType();
    }

    public void findTaskTypeByName(String name) {
        modelTaskTypeActivity.findTaskTypeByName(name);
    }

    public void addTaskType(TaskType taskType) {
        modelTaskTypeActivity.addTaskType(taskType);
    }

    public void findTaskTypeIdByName(String taskTypeName) {
        modelTaskTypeActivity.findTaskTypeIdByName(taskTypeName);
    }

    public void updateTaskType(TaskType taskType) {
        modelTaskTypeActivity.updateTaskType(taskType);
    }

    public void deleteTaskType(String taskTypeName) {
        modelTaskTypeActivity.deleteTaskType(taskTypeName);
    }

    public void deleteTaskItemByType(int taskType) {
        modelTaskTypeActivity.deleteTaskItemByType(taskType);
    }

    @Override
    public void onGetTaskTypeListResult(ArrayList<TaskType> taskTypes) {
        viewTaskTypeActivityListener.onGetTaskTypeListResult(taskTypes);
    }

    @Override
    public void onFindTaskTypeByNameFound(String taskTypeName) {
        viewTaskTypeActivityListener.onFindTaskTypeByNameFound(taskTypeName);
    }

    @Override
    public void onFindTaskTypeByNameNotFound(String taskTypeName) {
        viewTaskTypeActivityListener.onFindTaskTypeByNameNotFound(taskTypeName);
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        viewTaskTypeActivityListener.onFindTaskTypeIdByNameResult(id);
    }

    @Override
    public void onGetTaskListByTypeResult(ArrayList<TaskItem> taskItems) {
        viewTaskTypeActivityListener.onGetTaskListByTypeResult(taskItems);
    }


    public void getTaskListByType(int taskTypeId) {
        modelTaskTypeActivity.getTaskListByType(taskTypeId);
    }
}
