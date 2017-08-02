package com.hosiluan.reminder.view.viewMainActivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.database.DataSource;
import com.hosiluan.reminder.view.viewMainActivity.adapter.RecyclerViewTaskListAdapter;
import com.hosiluan.reminder.view.viewMainActivity.listener.RecyclerviewTaskListListener;
import com.hosiluan.reminder.view.viewMainActivity.listener.TaskListFragmentListener;

import java.util.ArrayList;

import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.CHECKED_POSTION;
import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.DEFAULT_TOOLBAR_FRAGMENT;
import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.DELETE_FRAGMENT;
import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.RECYCLERVIEW_ITEM_CAN_CLICK;
import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.TASK_LIST_RESULT;


/**
 * Created by HoSiLuan on 7/7/2017.
 */

public class TaskListFragment extends Fragment implements RecyclerviewTaskListListener {
    private View view;
    private RecyclerView mRecyclerViewTaskItems;
    private View mEmtyLayout;
    private TextView mTvEmptyList;
    private DataSource mDatasource;
    RecyclerViewTaskListAdapter recyclerViewTaskListAdapter;
    TaskListFragmentListener taskListFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskListFragmentListener = (TaskListFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        setWidget();

//        initRecyclerViewAllTask();

//        receiveTaskListResult();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("hoho","resume task list fragment");
        receiveTaskListResult();
//        initRecyclerViewAllTask();
    }

    public void setWidget() {
        mRecyclerViewTaskItems = view.findViewById(R.id.recylerview_task_list);
        mEmtyLayout = view.findViewById(R.id.empty_task_list);
        mDatasource = new DataSource(getActivity().getApplicationContext());
        mTvEmptyList = view.findViewById(R.id.tv_nothing_to_do);
//        recyclerViewTaskListAdapter = new RecyclerViewTaskListAdapter(getActivity(),new ArrayList<TaskItem>());

//        mRecyclerViewTaskItems.setOnLongClickListener();
//        mRecyclerViewTaskItems.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(getContext(), "Long click", Toast.LENGTH_SHORT).show();
////                if (mActionMode == null){
////                    mActionMode = view.startActionMode(callback);
////                }
//                return false;
//            }
//        });

    }

    public void receiveTaskListResult() {
        Bundle bundle = getArguments();
        ArrayList<TaskItem> taskItems = bundle.getParcelableArrayList(TASK_LIST_RESULT);
        int longClickSelectedPosition = bundle.getInt(CHECKED_POSTION);
        boolean isItemCanClick = bundle.getBoolean(RECYCLERVIEW_ITEM_CAN_CLICK);
        Log.d("fuck","receive task list from main " + taskItems.size());
        initRecyclerView(taskItems, longClickSelectedPosition, isItemCanClick);
    }


    public void initRecyclerView(ArrayList<TaskItem> list, int longClickSelectedPosition, boolean isItemCanClick) {
        Log.d("fuck","size thu 2 in task list fragment " + list.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTaskListAdapter = new RecyclerViewTaskListAdapter(getActivity(), list, this, longClickSelectedPosition, isItemCanClick);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), linearLayoutManager.getOrientation());
//        dividerItemDecoration.setDrawable(
//                ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.divider_shape)
//        );
//        mRecyclerViewTaskItems.addItemDecoration(dividerItemDecoration);

        mRecyclerViewTaskItems.setLayoutManager(linearLayoutManager);
        mRecyclerViewTaskItems.setAdapter(recyclerViewTaskListAdapter);

    }


    @Override
    public void sendTaskItemToTaskListFragment(Bundle bundle) {
        taskListFragmentListener.sendTaskItemToMainActivity(bundle);
    }

    @Override
    public void onLongRecyclerViewClick(int position) {
        taskListFragmentListener.onLongClickTaskListFragment(position);
    }

    @Override
    public void onDeleteSelectedItem(ArrayList<TaskItem> taskItems) {
        taskListFragmentListener.onDeleteSelectedItem(taskItems);
    }

    @Override
    public void onNoCheckboxChecked() {
        DeleteTaskItemFragment  deleteTaskItemFragment = (DeleteTaskItemFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DELETE_FRAGMENT);
        deleteTaskItemFragment.backToDefaultFragment();
    }

    public void getSelecterItemId(){
        recyclerViewTaskListAdapter.getSelectedItemId();
    }
}
