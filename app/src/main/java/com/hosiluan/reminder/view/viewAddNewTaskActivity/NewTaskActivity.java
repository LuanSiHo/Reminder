package com.hosiluan.reminder.view.viewAddNewTaskActivity;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hosiluan.reminder.AlarmReceiver;
import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.presenter.presenterAddNewTaskActivity.PresenterAddNewTaskActivity;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter.CustomSpinnerAddToListAdapter;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter.CustomSpinnerRepeatAdapter;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter.RecyclerViewCustomRepeatAdapter;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.listener.RecyclerViewCustomRepeatAdapterListener;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.listener.ViewAddNewTaskActivityListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.BUNDLE_FROM_MAIN_TO_NEW_ACTIVITY;
import static com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter.TASK_ID_BUNDLE;
import static com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter.TASK_REPEAT_BUNDLE;
import static com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter.TASK_TIME_BUNDLE;
import static com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter.TASK_TITLE_BUNDLE;
import static com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter.TASK_TYPE_BUNDLE;


public class NewTaskActivity extends AppCompatActivity implements ViewAddNewTaskActivityListener, RecyclerViewCustomRepeatAdapterListener {
    private EditText mEdtDate;
    private EditText mEdtTime;
    private EditText mEdtTaskName;

    private MySpinner mRepeatSpinner;
    private Spinner mAddToListSpinner;
    private FloatingActionButton mBtnSend;
    private ImageButton imgButtonBack;

    private ArrayList<TaskType> mTaskTypeList = new ArrayList<>();

    private Calendar mSelectedTime = Calendar.getInstance();
    private PresenterAddNewTaskActivity presenterAddNewTaskActivity;

    Intent intentFromRecyclerView;
    Bundle bundleFromRecyclerView;
    TaskItem taskItem = new TaskItem();

    private AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    RecyclerView recyclerViewRepeat;

    CustomSpinnerAddToListAdapter customSpinnerAddToListAdapter;
    ArrayList<String> repeatList = new ArrayList<>();
    ArrayList<String> customRepeatList = new ArrayList<>();
    ArrayList<String> selectedCustomRepeat = new ArrayList<>();
    ArrayList<String> checkedItem = new ArrayList<>();
    RecyclerViewCustomRepeatAdapter recyclerViewCustomRepeatAdapter;

    public Boolean canShowDialog = true;
    private boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setWidget();
        setEvent();

        presenterAddNewTaskActivity = new PresenterAddNewTaskActivity(getApplicationContext(), this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        initRepeatSpinner();
        presenterAddNewTaskActivity.initAddToListSpinner();

        intentFromRecyclerView = getIntent();
        bundleFromRecyclerView = intentFromRecyclerView.getBundleExtra(BUNDLE_FROM_MAIN_TO_NEW_ACTIVITY);
        getDataFromRecyclerViewTaskList();

        customRepeatList.add("Monday");
        customRepeatList.add("Tuesday");
        customRepeatList.add("Wednesday");
        customRepeatList.add("Thursday");
        customRepeatList.add("Friday");
        customRepeatList.add("Saturday");
        customRepeatList.add("Sunday");
    }

    /**
     * when click on task list item, change to this activity and get data from MainActivity by intent
     */
    public void getDataFromRecyclerViewTaskList() {

        if (bundleFromRecyclerView == null) {
            return;
        }
//        ddToListSpinner.setSelection(bundleFromRecyclerView.getInt(TASK_TYPE_BUNDLE));
        presenterAddNewTaskActivity.getTaskTypeById(bundleFromRecyclerView.getInt(TASK_TYPE_BUNDLE));
        Long time = bundleFromRecyclerView.getLong(TASK_TIME_BUNDLE);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String formattedTime = format.format(time);

        mEdtTaskName.setText(bundleFromRecyclerView.getString(TASK_TITLE_BUNDLE));
        mEdtDate.setText(formattedTime);

        format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        formattedTime = format.format(time);

        mEdtTime.setText(formattedTime);
        String repeat = bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE);
        if (bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE) == null) {
            Log.d("hihi", " null r");
        }
        switch (repeat) {
            case "-1": {
                mRepeatSpinner.setSelection(0);
                break;
            }
            case "1234567": {
                mRepeatSpinner.setSelection(1);
                break;
            }
            case "12345": {
                mRepeatSpinner.setSelection(2);
                break;
            }
            default: {
                mRepeatSpinner.setSelection(3);
                canShowDialog = false;
                checkedItem = changeNumberToDay(repeat);
                break;
            }
        }
    }

    public void setWidget() {
        mEdtDate = (EditText) findViewById(R.id.edt_new_task_date);
        mEdtTime = (EditText) findViewById(R.id.edt_new_task_time);
        mEdtTaskName = (EditText) findViewById(R.id.edt_new_task_name);

        mRepeatSpinner = (MySpinner) findViewById(R.id.spinner_repeat);
        mAddToListSpinner = (Spinner) findViewById(R.id.spinner_add_to_list);

        imgButtonBack = (ImageButton) findViewById(R.id.img_btn_add_new_task_back);
        mBtnSend = (FloatingActionButton) findViewById(R.id.btn_send);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(NewTaskActivity.this, AlarmReceiver.class);

    }

    public void setEvent() {
        mEdtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mSelectedTime.set(Calendar.YEAR, i);
                        mSelectedTime.set(Calendar.MONTH, i1);
                        mSelectedTime.set(Calendar.DAY_OF_MONTH, i2);

                        mEdtDate.setText(mSelectedTime.get(Calendar.DAY_OF_MONTH) + "/" + (mSelectedTime.get(Calendar.MONTH) + 1) + "/" + mSelectedTime.get(Calendar.YEAR));
                    }
                }, mSelectedTime.get(Calendar.YEAR), mSelectedTime.get(Calendar.MONTH), mSelectedTime.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        mEdtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewTaskActivity.this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        mSelectedTime.set(Calendar.HOUR_OF_DAY, i);
                        mSelectedTime.set(Calendar.MINUTE, i1);

                        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                        String formattedTime = format.format(mSelectedTime.getTime());
                        mEdtTime.setText(formattedTime);
                    }
                }, mSelectedTime.get(Calendar.HOUR_OF_DAY), mSelectedTime.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });

        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                taskItem.setmTaskText(mEdtTaskName.getText().toString());
                taskItem.setmTaskDate(mSelectedTime.getTime());
                ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
                taskItem.setmTaskIconColor(colorGenerator.getRandomColor());

                if (recyclerViewCustomRepeatAdapter != null) {
                    recyclerViewCustomRepeatAdapter.getSelectedItemDay();
                }

                String selectedRepeat = mRepeatSpinner.getSelectedItem().toString();

                switch (selectedRepeat) {
                    case "Once": {
                        taskItem.setmRepeat("-1");
                        break;
                    }
                    case "Daily": {
                        taskItem.setmRepeat("1234567");
                        break;
                    }
                    case "Mon to Fri": {
                        taskItem.setmRepeat("12345");
                        break;
                    }
                    case "Custom": {
                        String temp = "0";
                        Log.d("hihi", selectedCustomRepeat.size() + " size  dmmmm");
                        for (int i = 0; i < selectedCustomRepeat.size(); i++) {
                            switch (selectedCustomRepeat.get(i)) {
                                case "Monday": {
                                    temp += "1";
                                    break;
                                }
                                case "Tuesday": {
                                    temp += "2";
                                    break;
                                }
                                case "Wednesday": {
                                    temp += "3";
                                    break;
                                }
                                case "Thursday": {
                                    temp += "4";
                                    break;
                                }
                                case "Friday": {
                                    temp += "5";
                                    break;
                                }
                                case "Saturday": {
                                    temp += "6";
                                    break;
                                }
                                case "Sunday": {
                                    temp += "7";
                                    break;
                                }
                            }
                        }
                        taskItem.setmRepeat(temp);
                        Log.d("hihi", "repeat ne ma " + temp);
                        break;
                    }
                }
                String taskTypeName = mTaskTypeList.get(mAddToListSpinner.getSelectedItemPosition()).getmName();
                presenterAddNewTaskActivity.findTaskTypeIdByName(taskTypeName);
            }
        });


        mRepeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mRepeatSpinner.getSelectedItemPosition() == repeatList.size() - 1) {
                    if (canShowDialog) {
                        showCustomRepeatDialog(checkedItem);
                    }
                    canShowDialog = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public ArrayList<String> changeNumberToDay(String number) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i < number.length(); i++) {
            result.add(String.valueOf(number.charAt(i)));
        }
        return result;
    }

    public void showCustomRepeatDialog(ArrayList<String> checkItem) {
        View dialogView = LayoutInflater.from(NewTaskActivity.this).inflate(R.layout.custom_repeat_custom_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(NewTaskActivity.this);
        builder.setView(dialogView);

        recyclerViewRepeat = dialogView.findViewById(R.id.recyclerview_repeat);

        recyclerViewCustomRepeatAdapter = new
                RecyclerViewCustomRepeatAdapter(NewTaskActivity.this, customRepeatList, checkItem, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewTaskActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerViewRepeat.setLayoutManager(linearLayoutManager);
        recyclerViewRepeat.setAdapter(recyclerViewCustomRepeatAdapter);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        Button btnOk = dialogView.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCustomRepeatAdapter.getSelectedItemDay();
                cancel = false;
                alertDialog.dismiss();
            }
        });

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selectedCustomRepeat = taskItem.getmRepeat();
//                taskItem.setmRepeat(bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE));
//                Log.d("hello"," huhu "+ taskItem.getmRepeat());
                cancel = true;
                alertDialog.dismiss();
            }
        });

    }


    // check whether all edit texts are filled before insert
    public boolean checkEmptyTaskField() {
        boolean check = false;
        if (mEdtTaskName.getText().toString().equals("")) {
            viBrate("Enter task name");
            return true;
        }
        if (mEdtDate.getText().toString().equals("")) {
            viBrate("Enter date");
            return true;
        }
        if (mEdtTime.getText().toString().equals("")) {
            viBrate("Enter time");
            return true;
        }
        return check;
    }

    public void viBrate(String toastedText) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        Toast.makeText(NewTaskActivity.this, toastedText, Toast.LENGTH_SHORT).show();
    }

    public void initRepeatSpinner() {
        repeatList.add("Once");
        repeatList.add("Daily");
        repeatList.add("Mon to Fri");
        repeatList.add("Custom");
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,repeatList);

        CustomSpinnerRepeatAdapter arrayAdapter = new CustomSpinnerRepeatAdapter(getApplicationContext(), R.layout.custom_spinner_repeat, repeatList);
        mRepeatSpinner.setAdapter(arrayAdapter);
    }


    /**
     * Receive task type list from presenter
     *
     * @param taskTypes
     */

    @Override
    public void onGetTypeListResult(ArrayList<TaskType> taskTypes) {
        taskTypes.add(0, new TaskType("Default"));
        Log.d("hello", "task type size again " + taskTypes.size());
        mTaskTypeList = taskTypes;
        customSpinnerAddToListAdapter = new CustomSpinnerAddToListAdapter(getApplicationContext(), R.layout.custom_spinner_add_to_list, taskTypes);
        mAddToListSpinner.setAdapter(customSpinnerAddToListAdapter);
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        taskItem.setmTaskType(id);
        Log.d("hello", id + " task type ne");
        if (!checkEmptyTaskField()) {

            // bundleFromRecyclerView is null mean  insert task item by click add button,
            // else mean  select item from recyclerview and update it
            if (bundleFromRecyclerView == null) {

                presenterAddNewTaskActivity.addTaskItem(taskItem);
                finish();
            } else {
                taskItem.setmId(bundleFromRecyclerView.getInt(TASK_ID_BUNDLE));

//                taskItem.setmRepeat(bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE));

                Long time = bundleFromRecyclerView.getLong(TASK_TIME_BUNDLE);
                String title = bundleFromRecyclerView.getString(TASK_TITLE_BUNDLE);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String formattedDay = format.format(time);
                format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                String formattedTime = format.format(time);

                String timeFromIntent = formattedDay + formattedTime;
                String timeFromTextView = mEdtDate.getText().toString() + mEdtTime.getText().toString();

                String repeatFromIntent = bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE);
                String repeat = taskItem.getmRepeat();

                if (cancel) {
                    repeat = bundleFromRecyclerView.getString(TASK_REPEAT_BUNDLE);
                }

                //nothing change, do not update
                if ((title.equals(mEdtTaskName.getText().toString()) && (timeFromIntent.equals(timeFromTextView))
                        && bundleFromRecyclerView.getInt(TASK_TYPE_BUNDLE) == taskItem.getmTaskType()
                        && repeatFromIntent.equals(repeat) || repeat.equals("0"))) {
                    Toast.makeText(NewTaskActivity.this, "Task not Modified", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (timeFromIntent.equals(timeFromTextView)) {
                        taskItem.setmTaskDate(new Date(bundleFromRecyclerView.getLong(TASK_TIME_BUNDLE)));
                    }
                }
                presenterAddNewTaskActivity.updateTaskItem(taskItem);

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }
    }

    @Override
    public void onGetTaskTypeByIdResult(TaskType taskType) {
        Log.d("haiz",taskType.getmName()+ "  + id " + taskType.getmId() + " name ne");
        int spinnerPostion = customSpinnerAddToListAdapter.getPosition(taskType);
        Log.d("haiz","size cuar adapter " + customSpinnerAddToListAdapter.getCount());
        Log.d("haiz",spinnerPostion + " met r nha");

        Log.d("haiz",taskType.getmName() + " | " + taskType.getmId() + " | "
        + taskType.getmAmountOfTask() + " | " + taskType.getmIcon());

        for (int i = 0 ; i< customSpinnerAddToListAdapter.getCount(); i++){
            Log.d("haiz",customSpinnerAddToListAdapter.getItem(i).getmName() + " | " +
            customSpinnerAddToListAdapter.getItem(i).getmId() + " | " +
                    customSpinnerAddToListAdapter.getItem(i).getmAmountOfTask() + " | " +
                    customSpinnerAddToListAdapter.getItem(i).getmIcon()+ " | " );
        }
        mAddToListSpinner.setSelection(customSpinnerAddToListAdapter.getPosition(new TaskType(0,taskType.getmName(),taskType.getmId(),0)));

    }

    @Override
    public void onGetSelectedDay(ArrayList<String> list) {
        //lấy dc danh sách ngày r nè má
        selectedCustomRepeat = list;
    }


}
