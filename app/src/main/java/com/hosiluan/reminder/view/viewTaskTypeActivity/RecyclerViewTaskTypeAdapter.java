package com.hosiluan.reminder.view.viewTaskTypeActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/25/2017.
 */

public class RecyclerViewTaskTypeAdapter extends RecyclerView.Adapter<RecyclerViewTaskTypeAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<TaskType> taskTypes;
    private ArrayList<TaskItem> taskItems;
    private  RecyclerViewTaskTypeAdapterListener recyclerViewTaskTypeAdapterListener;

    public RecyclerViewTaskTypeAdapter(Context mContext, ArrayList<TaskType> taskTypes, RecyclerViewTaskTypeAdapterListener recyclerViewTaskTypeAdapterListener) {
        this.mContext = mContext;
        this.taskTypes = taskTypes;
        this.recyclerViewTaskTypeAdapterListener = recyclerViewTaskTypeAdapterListener;
    }

    public void getTaskItemByType(ArrayList<TaskItem> taskItems1){
        taskItems = taskItems1;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tasktype_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("hello","the hell id " + taskTypes.get(position).getmId());
        recyclerViewTaskTypeAdapterListener.getTaskListByType(taskTypes.get(position).getmId());

        holder.tvTaskType.setText(taskTypes.get(position).getmName());
        holder.tvAmountOfTask.setText("Tasks: " + taskItems.size());

        holder.imgBtnEditTaskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewTaskTypeAdapterListener.editTaskType(taskTypes.get(position).getmName());
            }
        });

        holder.imgBtnDeleteTaskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewTaskTypeAdapterListener.deleteTaskType(taskTypes.get(position).getmName());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewTaskTypeAdapterListener.itemSelected(taskTypes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskTypes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTaskType,tvAmountOfTask;
        ImageButton imgBtnEditTaskType, imgBtnDeleteTaskType;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTaskType = itemView.findViewById(R.id.tv_task_type_name);
            tvAmountOfTask = itemView.findViewById(R.id.tv_amount_of_task);

            imgBtnEditTaskType = itemView.findViewById(R.id.img_btn_edit_task_type);
            imgBtnDeleteTaskType = itemView.findViewById(R.id.img_btn_delete_task_type);
        }
    }


}
