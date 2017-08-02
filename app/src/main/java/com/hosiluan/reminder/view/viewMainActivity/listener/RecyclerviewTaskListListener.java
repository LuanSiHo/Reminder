package com.hosiluan.reminder.view.viewMainActivity.listener;

import android.os.Bundle;

import com.hosiluan.reminder.Item.TaskItem;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/19/2017.
 */

public interface RecyclerviewTaskListListener {
    void sendTaskItemToTaskListFragment(Bundle bundle);
    void onLongRecyclerViewClick(int position);
    void onDeleteSelectedItem(ArrayList<TaskItem> taskItems);
    void onNoCheckboxChecked();
}
