package com.hosiluan.reminder.view.viewMainActivity.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.presenter.presenterMainActivity.PresenterMainActivity;
import com.hosiluan.reminder.view.viewMainActivity.adapter.CustomSpinnerAdapter;
import com.hosiluan.reminder.view.viewMainActivity.listener.DefaultToolbarFragmentListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.VIBRATOR_SERVICE;
import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.GET_TYPE_LIST_RESULT;

/**
 * Created by HoSiLuan on 7/14/2017.
 */


public class DefaultToolBarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Serializable {
    public static final String SEARCH_BAR_FRAGMENT = "search bar fragment";
    private ImageButton mImgButtonSearch;
    private Spinner mSpinner;

    private NotificationCompat.Builder mBuilder;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public ArrayList<TaskType> mTaskType = new ArrayList<>();
    private DefaultToolbarFragmentListener defaultToolbarFragmentListener;

    private PresenterMainActivity mPresenterMainActivity;

    private EditText edtTaskListName;
    private AlertDialog alertDialog;
    private CustomSpinnerAdapter customSpinnerAdapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        defaultToolbarFragmentListener = (DefaultToolbarFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar, container, false);
        mImgButtonSearch = view.findViewById(R.id.img_btn_search);
        mSpinner = view.findViewById(R.id.spinner);

        mImgButtonSearch.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(this);
        GetTaskTypeListFromActivity();

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        mBuilder = new NotificationCompat.Builder(getActivity());

//
//
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//
////
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });
        return view;
    }


    /**
     * Received task type list from Main activity
     */
    public void GetTaskTypeListFromActivity() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<TaskType> taskTypes = bundle.getParcelableArrayList(GET_TYPE_LIST_RESULT);
            initTaskTypeSpinner(taskTypes);
        }
    }

    @Override
    public void onResume() {
        Log.d("boring",mSpinner.getSelectedItemPosition() + " selected item oposition");
//        setDefaultSelection();
//        mTaskType.remove(new TaskType("New List"));
//        initTaskTypeSpinner(mTaskType);
        defaultToolbarFragmentListener.loadAllTaskType();
//        GetTaskTypeListFromActivity();
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_search: {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SearchBarFragment searchBarFragment = new SearchBarFragment();
                fragmentTransaction.add(R.id.fragment_tool_bar, searchBarFragment, SEARCH_BAR_FRAGMENT);
                fragmentTransaction.addToBackStack(SEARCH_BAR_FRAGMENT);
                fragmentTransaction.commit();


//
//
////                startActivity(new Intent(MainActivity.this, SearchActivity.class));
//
////                mDefaultToolbar.getMenu().clear();
////                File file = new File(getApplicationInfo().dataDir + "/databases/" + DB_NAME);
////                if (file.exists()) {
////                    Log.d("hello", "data found");
//////                    SQLiteDatabase.deleteDatabase(file);
//////                    Log.d("hello","deleted");
////                } else {
////                    Log.d("hello", "data not found");
////                }
//
////                Intent intentFromRecyclerView  = new Intent(MainActivity.this, AlarmReciever.class);
////
////                Calendar calendar = Calendar.getInstance();
////                calendar.setTimeInMillis(System.currentTimeMillis());
////
////                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intentFromRecyclerView,PendingIntent.FLAG_UPDATE_CURRENT);
////                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
////                        SystemClock.elapsedRealtime() +
////                                60 * 1000, pendingIntent);
////
////                Toast.makeText(MainActivity.this, "helloooooooooo", Toast.LENGTH_SHORT).show();
////                mBuilder.setSmallIcon(R.drawable.ic_home)
////                        .setColor(Color.BLACK)
////                        .setContentText("My notification ")
////                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
////                        .setContentTitle("WTF");
////                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////                Notification notification = mBuilder.build();
////                notificationManager.notify(1,notification);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String taskTypeName = "";

        if (position == (mTaskType.size() - 1)) {
            showAddTaskListDialog();
        } else if (position == 0) {
            Log.d("fuck", "load all 0");
//            defaultToolbarFragmentListener.loadAllTaskType();
            taskTypeName = "All Lists";
        } else {
            taskTypeName = mTaskType.get(mSpinner.getSelectedItemPosition()).getmName();
        }
        defaultToolbarFragmentListener.findTaskTypeIdByName(taskTypeName);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * show the add task list dialog when clicking New List
     */
    private void showAddTaskListDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_add_task_type, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        edtTaskListName = dialogView.findViewById(R.id.edt_new_list_name);
        Button btnAdd = dialogView.findViewById(R.id.btn_new_list_add);
        Button btnCancel = dialogView.findViewById(R.id.btn_new_list_cancel);

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();


        // when closing the add new task type dialog, set the default item is "All List" / the first item
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                setDefaultSelection();
//                mSpinner.setSelection(0);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
                setDefaultSelection();
//                alertDialog.dismiss();
//                mSpinner.setSelection(0);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTypeName = edtTaskListName.getText().toString();
                // empty edit text
                if (taskTypeName.isEmpty()) {
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    Toast.makeText(getActivity(), "Please Enter List Name", Toast.LENGTH_SHORT).show();
                } else {

                    defaultToolbarFragmentListener.findTaskTypeByName(taskTypeName);
                    // check is task type is already in db
//                    mPresenterMainActivity.findTaskTypeByName(taskTypeName);
                }
            }
        });
    }

    /**
     * push the received list  into spinner
     *
     * @param mTaskTypeList
     */
    public void initTaskTypeSpinner(ArrayList<TaskType> mTaskTypeList) {

        mTaskType = mTaskTypeList;

        // order list  A - Z
        Collections.sort(mTaskTypeList, new Comparator<TaskType>() {
            @Override
            public int compare(TaskType taskType, TaskType t1) {
                return taskType.getmName().compareTo(t1.getmName());
            }
        });

        if (mTaskTypeList.size() == 0) {
            mTaskTypeList.add(0, new TaskType("All Lists"));
            mTaskTypeList.add(1, new TaskType("Default",-1));
        } else {
            if (!mTaskTypeList.get(0).getmName().equals("All Lists")) {
                mTaskTypeList.add(0, new TaskType("All Lists"));
            }
            if (!mTaskTypeList.get(1).getmName().equals("Default")) {
                mTaskTypeList.add(1, new TaskType("Default",-1));
            }
        }
        if (!mTaskTypeList.get(mTaskTypeList.size() - 1).getmName().equals("New List")) {
            mTaskTypeList.add(new TaskType("New List"));
        }

        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), R.layout.custom_spinner_item, mTaskType);
        mSpinner.setAdapter(customSpinnerAdapter);
    }


    public void dismissDialog() {
        if (alertDialog == null) {
            Toast.makeText(getActivity(), "dialog null cmnr", Toast.LENGTH_SHORT).show();
        } else {
            alertDialog.dismiss();
        }
    }

    public void setDefaultSelection() {
        mSpinner.setSelection(0);
    }

    public void setSpecificSelection(TaskType taskType) {
        mSpinner.setSelection(customSpinnerAdapter.getPosition(taskType));
        Log.d("boring",mSpinner.getSelectedItemPosition() + " selected item oposition");
    }

    public TaskType getSelectedItem() {
        return mTaskType.get(mSpinner.getSelectedItemPosition());
    }
}
