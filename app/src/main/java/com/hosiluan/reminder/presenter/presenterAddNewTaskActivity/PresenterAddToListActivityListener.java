package com.hosiluan.reminder.presenter.presenterAddNewTaskActivity;

import com.hosiluan.reminder.BaseInterface;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.presenter.presenterMainActivity.PresenterMainActivityListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/18/2017.
 */

public interface PresenterAddToListActivityListener   extends BaseInterface{

    void onFindTaskTypeIdByNameResult(int id);

    void onGetTaskTypeByIdResult(TaskType taskType);
}
