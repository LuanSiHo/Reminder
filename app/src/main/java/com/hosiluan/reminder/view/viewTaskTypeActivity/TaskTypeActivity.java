package com.hosiluan.reminder.view.viewTaskTypeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.presenter.presenterTaskTypeActivity.PresenterTaskTypeActivity;
import com.hosiluan.reminder.view.viewMainActivity.fragment.DefaultToolBarFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.DEFAULT_TOOLBAR_FRAGMENT;

public class TaskTypeActivity extends AppCompatActivity implements ViewTaskTypeActivityListener, RecyclerViewTaskTypeAdapterListener {


    public static final String RETURN_INTENT = "return intent id";
    private Toolbar defaultToolbar;
    private ImageButton imgBtnBack, imgBtnAddTaskType;

    private PresenterTaskTypeActivity presenterTaskTypeActivity;
    private RecyclerView recyclerViewTaskType;
    private EditText edtTaskListName;
    private EditText edtEditTaskTypeName;

    private AlertDialog AddTaskTypeAlertDialog;
    private AlertDialog EditTaskTypeAlertDialog;
    private AlertDialog deleteTaskTypeDialog;
    private TaskType mTaskType = new TaskType();
    private RecyclerViewTaskTypeAdapter recyclerViewTaskTypeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_type);
        setWidget();
        setSupportActionBar(defaultToolbar);
        setEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterTaskTypeActivity.loadTaskType();
    }

    public void setWidget() {
        defaultToolbar = (Toolbar) findViewById(R.id.task_type_default_toolbar);
        imgBtnBack = (ImageButton) findViewById(R.id.img_btn_back);
        imgBtnAddTaskType = (ImageButton) findViewById(R.id.img_btn_add_task_type);
        presenterTaskTypeActivity = new PresenterTaskTypeActivity(this, getApplicationContext());
        recyclerViewTaskType = (RecyclerView) findViewById(R.id.recyclerview_task_type);
    }

    public void setEvent() {

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultToolBarFragment defaultToolBarFragment = (DefaultToolBarFragment) getSupportFragmentManager().findFragmentByTag(DEFAULT_TOOLBAR_FRAGMENT);
                if (defaultToolBarFragment != null){
                    Log.d("hes loo", "hes loo");
                    Toast.makeText(TaskTypeActivity.this, "hes loo", Toast.LENGTH_SHORT).show();
                    defaultToolBarFragment.setDefaultSelection();
                }
                Log.d("hes loo", "hes loo boy ");
                Intent returnIntent = new Intent();
                setResult(0,returnIntent);
                finish();
            }
        });

        imgBtnAddTaskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskTypeDialog();
            }
        });
    }

    @Override
    public void onGetTaskTypeListResult(ArrayList<TaskType> taskTypes) {

        Collections.sort(taskTypes, new Comparator<TaskType>() {
            @Override
            public int compare(TaskType taskType, TaskType t1) {
                return taskType.getmName().compareTo(t1.getmName());
            }
        });

        recyclerViewTaskTypeAdapter = new
                RecyclerViewTaskTypeAdapter(getApplicationContext(), taskTypes, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerViewTaskType.setLayoutManager(linearLayoutManager);
        recyclerViewTaskType.setAdapter(recyclerViewTaskTypeAdapter);

    }


    public void showAddTaskTypeDialog() {
        View dialogView = LayoutInflater.from(TaskTypeActivity.this).inflate(R.layout.custom_dialog_add_task_type, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskTypeActivity.this);
        builder.setView(dialogView);
        AddTaskTypeAlertDialog = builder.create();
        AddTaskTypeAlertDialog.show();

        edtTaskListName = dialogView.findViewById(R.id.edt_new_list_name);
        Button btnAdd = dialogView.findViewById(R.id.btn_new_list_add);
        Button btnCancel = dialogView.findViewById(R.id.btn_new_list_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskTypeAlertDialog.dismiss();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTypeName = edtTaskListName.getText().toString();
                // empty edit text
                if (taskTypeName.isEmpty()) {
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(100);

                    Toast.makeText(TaskTypeActivity.this, "Please Enter List Name", Toast.LENGTH_SHORT).show();
                } else {
//                    defaultToolbarFragmentListener.findTaskTypeByName(taskTypeName);
                    // check is task type is already in db

                    presenterTaskTypeActivity.findTaskTypeByName(taskTypeName);
//                    mPresenterMainActivity.findTaskTypeByName(taskTypeName);
                }
            }
        });
    }


    @Override
    public void onFindTaskTypeByNameFound(String taskTypeName) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        Toast.makeText(TaskTypeActivity.this, taskTypeName + " is already in your list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFindTaskTypeByNameNotFound(String taskTypeName) {
        if (AddTaskTypeAlertDialog != null) {
            presenterTaskTypeActivity.addTaskType(new TaskType(taskTypeName));
            AddTaskTypeAlertDialog.dismiss();
        } else if (EditTaskTypeAlertDialog != null) {
            mTaskType.setmName(taskTypeName);
            presenterTaskTypeActivity.updateTaskType(mTaskType);
            EditTaskTypeAlertDialog.dismiss();
        }
        presenterTaskTypeActivity.loadTaskType();

    }


    @Override
    public void editTaskType(String taskTypeName) {
        showEditTaskTypeDialog(taskTypeName);
    }

    @Override
    public void deleteTaskType(String taskTypeName) {
        showDeleteTaskTypeDialog(taskTypeName);
    }

    @Override
    public void itemSelected(TaskType taskType) {
        Intent returnIntent  = new Intent();
        returnIntent.putExtra(RETURN_INTENT,taskType);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void getTaskListByType(int taskTypeId) {
        presenterTaskTypeActivity.getTaskListByType(taskTypeId);
    }

    public void showDeleteTaskTypeDialog(final String taskTypeName) {
        View dialogView = LayoutInflater.from(TaskTypeActivity.this).inflate(R.layout.custom_dialog_delete_task_type, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskTypeActivity.this);
        builder.setView(dialogView);

        deleteTaskTypeDialog = builder.create();
        deleteTaskTypeDialog.show();

        Button btnDelete = dialogView.findViewById(R.id.btn_delete_task_type);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel_delete_task_type);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTaskTypeDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterTaskTypeActivity.findTaskTypeIdByName(taskTypeName);
                presenterTaskTypeActivity.deleteTaskItemByType(mTaskType.getmId());
                presenterTaskTypeActivity.deleteTaskType(taskTypeName);
                presenterTaskTypeActivity.loadTaskType();
                deleteTaskTypeDialog.dismiss();
            }
        });
    }

    public void showEditTaskTypeDialog(String taskTypeName) {
        View dialogView = LayoutInflater.from(TaskTypeActivity.this).inflate(R.layout.custom_dialog_edit_task_type, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskTypeActivity.this);
        builder.setView(dialogView);
        EditTaskTypeAlertDialog = builder.create();
        EditTaskTypeAlertDialog.show();

        edtEditTaskTypeName = dialogView.findViewById(R.id.edt_edit_task_type);
        Button btnSave = dialogView.findViewById(R.id.btn_save_edit_task_type);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel_edit_task_type);

        edtEditTaskTypeName.setText(taskTypeName);
        mTaskType.setmName(taskTypeName);

        presenterTaskTypeActivity.findTaskTypeIdByName(edtEditTaskTypeName.getText().toString());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTaskTypeAlertDialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTypeName = edtEditTaskTypeName.getText().toString();
                // empty edit text
                if (taskTypeName.isEmpty()) {
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(100);

                    Toast.makeText(TaskTypeActivity.this, "Please Enter Task Type Name", Toast.LENGTH_SHORT).show();
                } else {
                    // check is task type is already in db

                    presenterTaskTypeActivity.findTaskTypeByName(taskTypeName);
                }
            }
        });
    }

    @Override
    public void onFindTaskTypeIdByNameResult(int id) {
        mTaskType.setmId(id);
    }

    @Override
    public void onGetTaskListByTypeResult(ArrayList<TaskItem> taskItems) {
        recyclerViewTaskTypeAdapter.getTaskItemByType(taskItems);
    }
}
