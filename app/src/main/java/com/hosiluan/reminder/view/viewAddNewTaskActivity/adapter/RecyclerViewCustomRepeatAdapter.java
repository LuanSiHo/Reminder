package com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hosiluan.reminder.R;
import com.hosiluan.reminder.view.viewAddNewTaskActivity.listener.RecyclerViewCustomRepeatAdapterListener;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/21/2017.
 */

public class RecyclerViewCustomRepeatAdapter extends RecyclerView.Adapter<RecyclerViewCustomRepeatAdapter.MyHolder> {


    private Context mContext;
    private ArrayList<String> list;
    ArrayList<String> selectedDay = new ArrayList<>();
    ArrayList<String> checkedItem = new ArrayList<>();
    RecyclerViewCustomRepeatAdapterListener recyclerViewCustomRepeatAdapterListener;

    public RecyclerViewCustomRepeatAdapter(Context mContext, ArrayList<String> list , ArrayList<String> checkedItem,RecyclerViewCustomRepeatAdapterListener recyclerViewCustomRepeatAdapterListener) {
        this.mContext = mContext;
        this.list = list;
        this.recyclerViewCustomRepeatAdapterListener = recyclerViewCustomRepeatAdapterListener;
        this.checkedItem = checkedItem;

        selectedDay.add("Monday");
        selectedDay.add("Tuesday");
        selectedDay.add("Wednesday");
        selectedDay.add("Thursday");
        selectedDay.add("Friday");
        selectedDay.add("Saturday");
        selectedDay.add("Sunday");
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.custom_repeat_custom_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.tvDate.setText(list.get(position));
        holder.checkboxDate.setChecked(true);

        for (int i = 0; i < checkedItem.size(); i ++){
            if (position  == Integer.parseInt(checkedItem.get(i)) - 1){
                holder.checkboxDate.setChecked(true);
                break;
            }else {
                holder.checkboxDate.setChecked(false);
            }
        }
        if (!holder.checkboxDate.isChecked()){
            selectedDay.remove(list.get(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkboxDate.isChecked()) {
                    holder.checkboxDate.setChecked(false);
                } else {
                    holder.checkboxDate.setChecked(true);
                }
            }
        });

        holder.checkboxDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()){
                    holder.checkboxDate.setChecked(false);
                    selectedDay.remove(list.get(position));
                }else {
                    holder.checkboxDate.setChecked(true);
                    String day = list.get(position);
                    selectedDay.add(day);
                }
            }
        });
    }

    public void getSelectedItemDay(){
        Log.d("hello","select day " + selectedDay.size());
        recyclerViewCustomRepeatAdapterListener.onGetSelectedDay(selectedDay);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        CheckBox checkboxDate;

        public MyHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_custom_repeat_dialog_item_date);
            checkboxDate = itemView.findViewById(R.id.checkbox_custom_repeat_dialog_item_date);
        }
    }
}
