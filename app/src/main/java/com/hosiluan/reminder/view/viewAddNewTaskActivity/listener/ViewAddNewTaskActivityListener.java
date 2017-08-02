package com.hosiluan.reminder.view.viewAddNewTaskActivity.listener;

import com.hosiluan.reminder.BaseInterface;
import com.hosiluan.reminder.Item.TaskType;

/**
 * Created by HoSiLuan on 7/18/2017.
 */

public interface ViewAddNewTaskActivityListener   extends BaseInterface{
    void onFindTaskTypeIdByNameResult(int id);

    void onGetTaskTypeByIdResult(TaskType taskType);
}
