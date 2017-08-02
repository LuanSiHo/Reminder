package com.hosiluan.reminder.view.viewMainActivity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hosiluan.reminder.Item.TaskItem;
import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;
import com.hosiluan.reminder.database.DataSource;
import com.hosiluan.reminder.view.viewMainActivity.MainActivity;
import com.hosiluan.reminder.view.viewMainActivity.fragment.DeleteTaskItemFragment;
import com.hosiluan.reminder.view.viewMainActivity.listener.RecyclerviewTaskListListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by HoSiLuan on 7/7/2017.
 */

public class RecyclerViewTaskListAdapter extends RecyclerView.Adapter<RecyclerViewTaskListAdapter.MyViewHolder> {

    public static final String TASK_TITLE_BUNDLE = "task title";
    public static final String TASK_TIME_BUNDLE = "task date";
    public static final String BUNDLE = "bundle";
    public static final String TASK_ID_BUNDLE = "task id";
    public static final String TASK_TYPE_BUNDLE = "task type";
    public static final String TASK_REPEAT_BUNDLE = "task repeat bundle";
    private Context mContext;
    private ArrayList<TaskItem> mListItem;
    public int checkedPosition;
    MainActivity mMainActivity;
    RecyclerviewTaskListListener recyclerviewTaskListListener;
    int longClickSelectedPosition;
    boolean isItemCanClick;
    int amountOfSelectedItem = 1;
    ArrayList<TaskItem> selectedItemId;


    public RecyclerViewTaskListAdapter(Context mContext, ArrayList<TaskItem> mListItem, RecyclerviewTaskListListener recyclerviewTaskListListener
            , int longClickSelectedPosition, boolean isItemCanClick) {
        this.mContext = mContext;
        this.mListItem = mListItem;
        this.mMainActivity = (MainActivity) mContext;
        this.recyclerviewTaskListListener = recyclerviewTaskListListener;
        this.longClickSelectedPosition = longClickSelectedPosition;
        this.isItemCanClick = isItemCanClick;
        selectedItemId = new ArrayList<>();
        if (longClickSelectedPosition < mListItem.size()) {
            selectedItemId.add(mListItem.get(longClickSelectedPosition));
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, mMainActivity);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (mMainActivity.isLongClick) {
            holder.checkBoxTaskItem.setVisibility(View.VISIBLE);
            if (position == longClickSelectedPosition) {
                holder.checkBoxTaskItem.setChecked(true);
            }
        } else {
            holder.checkBoxTaskItem.setVisibility(View.INVISIBLE);
        }

        holder.tvTaskText.setText(mListItem.get(position).getmTaskText());
        DataSource dataSource = new DataSource(mContext);
        int typeId = mListItem.get(position).getmTaskType();
        if (typeId == -1) {
            holder.tvType.setText("Default");
        } else {
            TaskType type = dataSource.getTaskTypeById(typeId);
            holder.tvType.setText(type.getmName());
        }

        //format task time
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy hh:mm a", Locale.ENGLISH);
        final String formattedTime = dateFormat.format(mListItem.get(position).getmTaskDate());
        holder.tvTaskDate.setText(formattedTime);

        //custom task icon
//        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
//        mListItem.get(position).setmTaskIconColor(colorGenerator.getRandomColor());
        TextDrawable.IBuilder iBuilder = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .toUpperCase()
                .bold()
                .endConfig()
                .round();

        TextDrawable textDrawable = iBuilder.build(String.valueOf(holder.tvTaskText.getText().charAt(0)), mListItem.get(position).getmTaskIconColor());
        holder.imgIcon.setImageDrawable(textDrawable);
        final TextView tvAmountOfSelectedItem = (TextView) mMainActivity.findViewById(R.id.tv_amount_of_selected_item);


        holder.checkBoxTaskItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!compoundButton.isChecked()) {
                    holder.checkBoxTaskItem.setChecked(false);
                    amountOfSelectedItem--;
                    selectedItemId.remove(mListItem.get(position));
                    if (selectedItemId.size() == 0) {
                        recyclerviewTaskListListener.onNoCheckboxChecked();
                    }
                } else {
                    amountOfSelectedItem++;
                    holder.checkBoxTaskItem.setChecked(true);
                    selectedItemId.add(mListItem.get(position));
                }
                tvAmountOfSelectedItem.setText(amountOfSelectedItem + " item selected");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isItemCanClick) {
                    if (holder.checkBoxTaskItem.isChecked()) {
                        holder.checkBoxTaskItem.setChecked(false);
                    } else {
                        holder.checkBoxTaskItem.setChecked(true);
                    }
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString(TASK_TITLE_BUNDLE, mListItem.get(position).getmTaskText());
                    bundle.putLong(TASK_TIME_BUNDLE, mListItem.get(position).getmTaskDate().getTime());
                    bundle.putInt(TASK_ID_BUNDLE, mListItem.get(position).getmId());
                    bundle.putInt(TASK_TYPE_BUNDLE, mListItem.get(position).getmTaskType());
                    bundle.putString(TASK_REPEAT_BUNDLE,mListItem.get(position).getmRepeat());
                    recyclerviewTaskListListener.sendTaskItemToTaskListFragment(bundle);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerviewTaskListListener.onLongRecyclerViewClick(position);
                return true;
            }
        });

    }

    public void getSelectedItemId() {
        recyclerviewTaskListListener.onDeleteSelectedItem(selectedItemId);
    }


    @Override
    public int getItemCount() {
        return mListItem.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvTaskText;
        TextView tvTaskDate;
        TextView tvType;
        CheckBox checkBoxTaskItem;
        MainActivity mainActivity;

        public MyViewHolder(View itemView, MainActivity mainActivity) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_task_icon);
            tvTaskText = itemView.findViewById(R.id.tv_task_text);
            tvTaskDate = itemView.findViewById(R.id.tv_tast_date);
            tvType = itemView.findViewById(R.id.tv_task_type);
            checkBoxTaskItem = itemView.findViewById(R.id.checkbox_task_item);
            this.mainActivity = mainActivity;
        }
    }

}
