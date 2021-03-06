package com.hosiluan.reminder.view.viewTaskTypeActivity;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/25/2017.
 */

public interface ViewTaskTypeActivityListener {
    void onGetTaskTypeListResult(ArrayList<TaskType> taskTypes);
    void onFindTaskTypeByNameFound(String taskTypeName);
    void onFindTaskTypeByNameNotFound(String taskTypeName);
    void onFindTaskTypeIdByNameResult(int id);

    void onGetTaskListByTypeResult(ArrayList<TaskItem> taskItems);
}
