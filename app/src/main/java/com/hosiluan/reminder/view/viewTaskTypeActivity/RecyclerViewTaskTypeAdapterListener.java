package com.hosiluan.reminder.view.viewTaskTypeActivity;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;

/**
 * Created by HoSiLuan on 7/26/2017.
 */

public interface RecyclerViewTaskTypeAdapterListener {
    void editTaskType(String taskTypeName);
    void deleteTaskType(String taskTypeName);
    void itemSelected(TaskType taskType);
    void getTaskListByType(int taskTypeId);
}
