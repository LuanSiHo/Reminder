package com.hosiluan.reminder.view.viewMainActivity.listener;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/17/2017.
 */

public interface ViewMainActivityListener {

    void onGetTypeListResult(ArrayList<TaskType> taskTypes);

    void onTaskTypeFound(String taskTypeName);

    void onTaskTypeNotFound(String taskTypeName, ArrayList<TaskType> taskTypes);

    void getTaskListResult(ArrayList<TaskItem> taskItems);

    void onFindTaskTypeIdByNameResult(int id);
    void onFindTaskListByTypeResult(ArrayList<TaskItem> taskItems);

    void onFindTaskItemFound(ArrayList<TaskItem> taskItems);

    void onFindTaskItemNotFound();
}
