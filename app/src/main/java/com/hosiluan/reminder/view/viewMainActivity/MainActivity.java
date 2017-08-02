package com.hosiluan.reminder.view.viewMainActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hosiluan.reminder.AlarmReceiver;
import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.presenter.presenterMainActivity.PresenterMainActivity;
import com.hosiluan.reminder.view.viewTaskTypeActivity.TaskTypeActivity;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.NewTaskActivity;
import com.hosiluan.reminder.view.viewMainActivity.fragment.DefaultToolBarFragment;
import com.hosiluan.reminder.view.viewMainActivity.fragment.DeleteTaskItemFragment;
import com.hosiluan.reminder.view.viewMainActivity.fragment.EmptyListFragment;
import com.hosiluan.reminder.view.viewMainActivity.fragment.TaskListFragment;
import com.hosiluan.reminder.view.viewMainActivity.listener.DefaultToolbarFragmentListener;
import com.hosiluan.reminder.view.viewMainActivity.listener.DeleteTaskItemFragmentListener;
import com.hosiluan.reminder.view.viewMainActivity.listener.TaskListFragmentListener;
import com.hosiluan.reminder.view.viewMainActivity.listener.ViewMainActivityListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.hosiluan.reminder.database.DBHelper.DB_NAME;
import static com.hosiluan.reminder.view.viewTaskTypeActivity.TaskTypeActivity.RETURN_INTENT;

public class MainActivity extends AppCompatActivity implements ViewMainActivityListener, DefaultToolbarFragmentListener,
        TaskListFragmentListener, DeleteTaskItemFragmentListener {


    public static final String DEFAULT_TOOLBAR_FRAGMENT = "default toolbar fragment";
    private static final String RECYCLERVIEW_TASK_LIST_FRAGMENT = "recyclerview task list fragment";
    public static final String TASK_LIST_RESULT = "task list result";
    public static final String GET_TYPE_LIST_RESULT = "get type list result";
    public static final String BUNDLE_FROM_MAIN_TO_NEW_ACTIVITY = "bundle from main to new activity";
    public static final String CHECKED_POSTION = "checked position";
    public static final String RECYCLERVIEW_ITEM_CAN_CLICK = "recyclerview item can click";
    public static final String DELETE_FRAGMENT = "delete fragment";
    public static final String EMPTY_TASK_FRAGMENT = "empty task fragment";
    public static final int REQUEST_CODE = 111;
    private static final int REQUEST_CODE_TO_NEW_TASK = 112;


    public boolean isLongClick = false;
    private Toolbar mDefaultToolbar;
    private FloatingActionButton mFabAdd;
    private DefaultToolBarFragment fragment;
    public PresenterMainActivity mPresenterMainActivity;
    public ArrayList<TaskType> mTaskType = new ArrayList<>();

    private int checkedPosition;

    private AlarmManager alarmManager;
//    PendingIntent pendingIntent;
//    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        setEvent();
        mPresenterMainActivity.initTaskTypeSpinner();
        mPresenterMainActivity.loadTaskList();

//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 20 * 1000,
//                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("hoho", "resume main ");

//        mPresenterMainActivity.initTaskTypeSpinner();
    }

    public void setWidget() {
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mDefaultToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDefaultToolbar.inflateMenu(R.menu.menu_main);
        mDefaultToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(new Intent(MainActivity.this, TaskTypeActivity.class), REQUEST_CODE);
                return false;
            }
        });
        mPresenterMainActivity = new PresenterMainActivity(this, getApplicationContext());
    }

    /**
     * set  default toolbar layout
     *
     * @param fragment
     */
    public void setToolBarFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_tool_bar, fragment, DEFAULT_TOOLBAR_FRAGMENT);
        fragmentTransaction.addToBackStack(DEFAULT_TOOLBAR_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * set task list layout when the list have at least 1 item
     *
     * @param fragment
     */
    public void setListTaskFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.task_list_layout, fragment, RECYCLERVIEW_TASK_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }

    public void setEvent() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File file = new File(getApplicationInfo().dataDir + "/databases/" + DB_NAME);
//                if (file.exists()) {
//                    Log.d("hello", "data found");
//                    SQLiteDatabase.deleteDatabase(file);
//                    Log.d("hello", "deleted");
//                } else {
//                    Log.d("hello", "data not found");
//                }

                startActivity(new Intent(MainActivity.this, NewTaskActivity.class));

            }
        });

    }

    /**
     * Receive task type list from presenter
     *
     * @param taskTypes
     */
    @Override
    public void onGetTypeListResult(ArrayList<TaskType> taskTypes) {
        mTaskType = taskTypes;
        DefaultToolBarFragment defaultToolBarFragment = new DefaultToolBarFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GET_TYPE_LIST_RESULT, taskTypes);
        defaultToolBarFragment.setArguments(bundle);
        setToolBarFragment(defaultToolBarFragment);
    }

    /**
     * The task type name is already in database, do not add
     */
    @Override
    public void onTaskTypeFound(String taskTypeName) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        Toast.makeText(MainActivity.this, taskTypeName + " is already in your list", Toast.LENGTH_SHORT).show();
    }

    /**
     * The task type name is not in database, add to task type list
     */
    @Override
    public void onTaskTypeNotFound(String taskTypeName, ArrayList<TaskType> taskTypes) {

        DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
        //task type name is "All Lists" or "Default", don't add, they are default
        if (taskTypeName.equals("All Lists") || taskTypeName.equals("Default")) {
            defaultToolBarFragment.dismissDialog();
        } else {
            mPresenterMainActivity.addTaskType(new TaskType(taskTypeName));
            if (defaultToolBarFragment != null) {
                defaultToolBarFragment.setDefaultSelection();
                defaultToolBarFragment.dismissDialog();
                taskTypes.add(new TaskType(taskTypeName));
                defaultToolBarFragment.initTaskTypeSpinner(taskTypes);
            }
        }
    }

    /**
     * receive the tast list from presenter
     *
     * @param taskItems
     */
    @Override
    public void getTaskListResult(ArrayList<TaskItem> taskItems) {
        Log.d("hoho", "1");
        if (taskItems.size() == 0) {
            EmptyListFragment emptyListFragment = new EmptyListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.task_list_layout, emptyListFragment, EMPTY_TASK_FRAGMENT);
//            fragmentTransaction.addToBackStack(EMPTY_TASK_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            TaskListFragment taskListFragment = new TaskListFragment();
            Bundle bundle = new Bundle();
            if (checkedPosition >= taskItems.size()) {
                checkedPosition = 0;


            }
            bundle.putInt(CHECKED_POSTION, checkedPosition);
            bundle.putBoolean(RECYCLERVIEW_ITEM_CAN_CLICK, isLongClick);
            Log.d("fuck","task list result " + taskItems.size());
            bundle.putParcelableArrayList(TASK_LIST_RESULT, taskItems);
            taskListFragment.setArguments(bundle);
            setListTaskFragment(taskListFragment);
        }

        // call the alarm here

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < taskItems.size(); i++) {
            String repeat = taskItems.get(i).getmRepeat();

            if (repeat.equals("-1")) {
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                intent.putExtra("name", taskItems.get(i).getmTaskText());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (Calendar.getInstance().getTimeInMillis() < taskItems.get(i).getmTaskDate().getTime()) {
                    alarmManager1.set(AlarmManager.RTC_WAKEUP, taskItems.get(i).getmTaskDate().getTime(), pendingIntent);
                }

            } else if (repeat.equals("1234567")) {

                Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
                intent1.putExtra("name", taskItems.get(i).getmTaskText());

                setAlarm(2, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(3, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(4, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(5, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(6, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(7, taskItems.get(i).getmTaskDate().getTime(), intent1);
                setAlarm(1, taskItems.get(i).getmTaskDate().getTime(), intent1);

            } else if (repeat.equals("12345")) {
                Intent intent2 = new Intent(MainActivity.this, AlarmReceiver.class);
                intent2.putExtra("name", taskItems.get(i).getmTaskText());
                setAlarm(2, taskItems.get(i).getmTaskDate().getTime(), intent2);
                setAlarm(3, taskItems.get(i).getmTaskDate().getTime(), intent2);
                setAlarm(4, taskItems.get(i).getmTaskDate().getTime(), intent2);
                setAlarm(5, taskItems.get(i).getmTaskDate().getTime(), intent2);
                setAlarm(6, taskItems.get(i).getmTaskDate().getTime(), intent2);
            } else {
                Intent intent3 = new Intent(MainActivity.this, AlarmReceiver.class);
                intent3.putExtra("name", taskItems.get(i).getmTaskText());
                for (int j = 1; j < repeat.length(); j++) {
                    int day = Integer.parseInt(String.valueOf(repeat.charAt(j)));
                    if (day == 7) {
                        setAlarm(1, taskItems.get(i).getmTaskDate().getTime(), intent3);
                    }
                    setAlarm(day + 1, taskItems.get(i).getmTaskDate().getTime(), intent3);
                }
            }
        }
    }


    public void setAlarm(int dayOfWeek, Long time, Intent intent) {

        // Add this day of the week line to your existing code
        Calendar alarmCalendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("kk", Locale.ENGLISH);
        int hour = Integer.parseInt(format.format(time));

        format = new SimpleDateFormat("mm", Locale.ENGLISH);
        int minute = Integer.parseInt(format.format(time));

        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hour);
        alarmCalendar.set(Calendar.MINUTE, minute);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.MILLISECOND, 0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) alarmCalendar.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Also change the time to 24 hours.

        Calendar check = Calendar.getInstance();
//        check.set(Calendar.DAY_OF_WEEK, 1);
//        check.set(Calendar.HOUR_OF_DAY,0);
//        check.set(Calendar.MINUTE,0);
//
//        Log.d("hello",alarmCalendar.get(Calendar.DAY_OF_WEEK) + " / " + alarmCalendar.get(Calendar.HOUR_OF_DAY) +
//        " / " + alarmCalendar.get(Calendar.MINUTE));

        if (check.getTimeInMillis() < alarmCalendar.getTimeInMillis()) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, pendingIntent);
        }
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        mPresenterMainActivity.findTaskListByType(id);
    }

    @Override
    public void onFindTaskListByTypeResult(ArrayList<TaskItem> taskItems) {
//        Log.d("hihi",taskItems.get(0).getmTaskText() + " " +taskItems.get(0).getmRepeat());
        if (taskItems.size() == 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            EmptyListFragment emptyListFragment = (EmptyListFragment) getSupportFragmentManager().findFragmentByTag(EMPTY_TASK_FRAGMENT);

            if (emptyListFragment != null) {
                fragmentManager.popBackStack(EMPTY_TASK_FRAGMENT, 0);
            } else {
                EmptyListFragment myEmptyListFragment = new EmptyListFragment();
                fragmentTransaction.replace(R.id.task_list_layout, myEmptyListFragment, EMPTY_TASK_FRAGMENT);
            }
            fragmentTransaction.commit();

        } else {
            TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(RECYCLERVIEW_TASK_LIST_FRAGMENT);
            if (checkedPosition >= taskItems.size()) {
                checkedPosition = 0;
            }

            if (taskListFragment != null) {
                Log.d("hoho", "2");
                Log.d("hoho", "task list fragment != null");
                taskListFragment.initRecyclerView(taskItems, checkedPosition, isLongClick);

            } else {
                Log.d("hoho", "3");
                Log.d("hoho", "task list fragment == null");
                TaskListFragment myTaskListFragment = new TaskListFragment();
                Bundle bundle = new Bundle();
                if (checkedPosition >= taskItems.size()) {
                    checkedPosition = 0;
                }
                Log.d("fuck","task list result second " + taskItems.size());
                bundle.putInt(CHECKED_POSTION, checkedPosition);
                bundle.putBoolean(RECYCLERVIEW_ITEM_CAN_CLICK, isLongClick);
                bundle.putParcelableArrayList(TASK_LIST_RESULT, taskItems);
                myTaskListFragment.setArguments(bundle);
                Log.d("hoho", "4");
                setListTaskFragment(myTaskListFragment);
            }
        }

    }

    @Override
    public void onFindTaskItemFound(ArrayList<TaskItem> taskItems) {
        TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(RECYCLERVIEW_TASK_LIST_FRAGMENT);
        Log.d("hoho", "5");
        if (taskListFragment != null) {
            taskListFragment.initRecyclerView(taskItems, checkedPosition, isLongClick);
        }
    }

    @Override
    public void onFindTaskItemNotFound() {
        TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(RECYCLERVIEW_TASK_LIST_FRAGMENT);
        Log.d("hoho", "6");
        if (taskListFragment != null) {
            taskListFragment.initRecyclerView(new ArrayList<TaskItem>(), checkedPosition, isLongClick);
        }
    }


    public void findTaskItem(String taskItem) {
        mPresenterMainActivity.findTaskItem(taskItem);
    }

    /**
     * receive the task type name from task list fragment
     *
     * @param taskTypeName
     */
    @Override
    public void findTaskTypeByName(String taskTypeName) {
        mPresenterMainActivity.findTaskTypeByName(taskTypeName);
    }


    /**
     * call the presenter to find the task type id , already got task type name from spinner
     *
     * @param taskTypeName
     */
    @Override
    public void findTaskTypeIdByName(String taskTypeName) {
        mPresenterMainActivity.findTaskTypeIdByName(taskTypeName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
//                Log.d("fuck",data.getIntExtra(RETURN_INTENT));
                TaskType taskType = data.getParcelableExtra(RETURN_INTENT);
//                Log.d("hello",taskType.getmId() + " " + taskType.getmName());
//                mPresenterMainActivity.findTaskListByType(data.getIntExtra(RETURN_INTENT, -1));
                DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
                defaultToolBarFragment.setSpecificSelection(taskType);

//                Log.d("hello", data.getIntExtra(RETURN_INTENT, -1) + " id ne");
            }
            if (resultCode == 0){
                DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
                defaultToolBarFragment.setDefaultSelection();
            }
        }
        if (requestCode == REQUEST_CODE_TO_NEW_TASK) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("fuck", "load task list 1");
                mPresenterMainActivity.loadTaskList();
            }
        }
    }

    /**
     * call the presenter to load all the task from db
     */
    @Override
    public void loadAllTaskType() {
        Log.d("fuck", "load task list 2");
        mPresenterMainActivity.loadTaskList();
    }


    /**
     * receive the selected item info from task list fragment and send to new task activity
     *
     * @param bundle
     */
    @Override
    public void sendTaskItemToMainActivity(Bundle bundle) {

        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra(BUNDLE_FROM_MAIN_TO_NEW_ACTIVITY, bundle);
        startActivityForResult(intent, REQUEST_CODE_TO_NEW_TASK);
    }

    /**
     * receive the long click item position, show delete fragment, reload recyclerview
     *
     * @param postion
     */
    @Override
    public void onLongClickTaskListFragment(int postion) {

//        mDefaultToolbar.getMenu().clear();

        isLongClick = true;
        checkedPosition = postion;
        DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
        TaskType taskType = defaultToolBarFragment.getSelectedItem();

        DeleteTaskItemFragment deleteTaskItemFragment = new DeleteTaskItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_tool_bar, deleteTaskItemFragment, DELETE_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (taskType.getmName().equals("All Lists")) {
            Log.d("fuck", "load task list 3");
            mPresenterMainActivity.loadTaskList();
        } else {
            mPresenterMainActivity.findTaskTypeIdByName(taskType.getmName());
        }
    }

    /**
     * receive the list of item to delete, call presenter to delete, change toolbar back to default fragment
     *
     * @param taskItems
     */
    @Override
    public void onDeleteSelectedItem(ArrayList<TaskItem> taskItems) {

        if (taskItems.size() == 0) {
            EmptyListFragment emptyListFragment = new EmptyListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.task_list_layout, emptyListFragment, EMPTY_TASK_FRAGMENT);
            fragmentTransaction.addToBackStack(EMPTY_TASK_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            mPresenterMainActivity.deleteTaskItem(taskItems);
            isLongClick = false;
            Log.d("fuck", "load task list 4");
            mPresenterMainActivity.loadTaskList();

            DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
            defaultToolBarFragment.setDefaultSelection();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStack(DEFAULT_TOOLBAR_FRAGMENT, 0);
            fragmentTransaction.commit();
        }
    }

    /**
     * reload task list, set the recyclerview item clicked event to call new activity,
     */
    @Override
    public void onDeleteTaskItemFragmentBack() {
        isLongClick = false;
        DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
        defaultToolBarFragment.setDefaultSelection();
        Log.d("fuck", "load task list 5");
        mPresenterMainActivity.loadTaskList();
    }

    /**
     * call the task list fragment to get the list of item to delete
     */
    @Override
    public void onDeleteTaskItemFragmentDelete() {
        TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(RECYCLERVIEW_TASK_LIST_FRAGMENT);
        taskListFragment.getSelecterItemId();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
