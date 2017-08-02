package com.hosiluan.reminder.presenter.presenterMainActivity;

import android.content.Context;
import android.util.Log;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.model.modelMainActivity.ModelMainActivity;
import com.hosiluan.reminder.view.viewMainActivity.listener.ViewMainActivityListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/17/2017.
 */

public class PresenterMainActivity implements PresenterMainActivityListener {

    private Context mContext;
    private ViewMainActivityListener viewMainActivityListener;
//    private ViewTaskListFragmentListener viewTaskListFragmentListener;
    private ModelMainActivity modelMainActivity;


    public PresenterMainActivity(ViewMainActivityListener viewMainActivityListener, Context mContext) {
        this.mContext = mContext;
        this.viewMainActivityListener = viewMainActivityListener;
        modelMainActivity = new ModelMainActivity(this,mContext);
    }

//
//    public PresenterMainActivity(Context mContext, ViewTaskListFragmentListener viewTaskListFragmentListener) {
//        this.mContext = mContext;
//        this.viewTaskListFragmentListener = viewTaskListFragmentListener;
//        modelMainActivity = new ModelMainActivity(this,mContext);
//
//    }

    public void initTaskTypeSpinner() {
        modelMainActivity.initTaskTypeSpinner();
    }

    public void findTaskTypeByName(String taskTypeName ) {
        modelMainActivity.findTaskTypeByName(taskTypeName );
    }

    public void addTaskType(TaskType taskType){
        modelMainActivity.addTaskType(taskType);
    }


    public void loadTaskList(){
        Log.d("fuck","load task list");
        modelMainActivity.loadTaskList();
    }
    public void findTaskTypeIdByName(String taskTypeName) {
        modelMainActivity.findTaskTypeIdByName(taskTypeName);
    }

    public void findTaskListByType(int taskType){
     modelMainActivity.findTaskListByType(taskType);
    }

    public void deleteTaskItem(ArrayList<TaskItem> taskItems){
        modelMainActivity.deleteTaskItem(taskItems);
    }

    @Override
    public void onGetTypeListResult(ArrayList<TaskType> taskTypes) {
        viewMainActivityListener.onGetTypeListResult(taskTypes);
    }

    @Override
    public void onTaskTypeFound(String taskTypeName) {
        viewMainActivityListener.onTaskTypeFound(taskTypeName);
    }

    @Override
    public void onTaskTypeNotFound(String taskTypeName,ArrayList<TaskType> taskTypes) {
        viewMainActivityListener.onTaskTypeNotFound(taskTypeName,taskTypes);
    }

    @Override
    public void getTaskListResult(ArrayList<TaskItem> taskItems) {
        viewMainActivityListener.getTaskListResult(taskItems);
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        viewMainActivityListener.onFindTaskTypeIdByNameResult(id);
    }

    @Override
    public void onFindTaskListByTypeResult(ArrayList<TaskItem> taskItems) {
        viewMainActivityListener.onFindTaskListByTypeResult(taskItems);
    }

    @Override
    public void onFindTaskItemFound(ArrayList<TaskItem> taskItems) {
        viewMainActivityListener.onFindTaskItemFound(taskItems);
    }

    @Override
    public void onFindTaskItemNotFound() {
        viewMainActivityListener.onFindTaskItemNotFound();
    }

    public void findTaskItem(String taskItem) {
        modelMainActivity.findTaskItem(taskItem);
    }
}
