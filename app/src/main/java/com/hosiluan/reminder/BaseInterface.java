package com.hosiluan.reminder;

import com.hosiluan.reminder.Item.TaskType;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/18/2017.
 */

public interface BaseInterface {
    void onGetTypeListResult(ArrayList<TaskType> taskTypes);

}
