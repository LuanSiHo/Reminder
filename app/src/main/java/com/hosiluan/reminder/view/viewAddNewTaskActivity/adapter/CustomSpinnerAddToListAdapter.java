package com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/6/2017.
 */

public class CustomSpinnerAddToListAdapter extends ArrayAdapter<TaskType> {

    private Context mContext;
    private ArrayList<TaskType> mList;

    public CustomSpinnerAddToListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TaskType> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_add_to_list_default,parent,false);
        TextView textView = convertView.findViewById(R.id.tv_add_to_list_default);
        textView.setText(mList.get(position).getmName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_add_to_list,parent,false);
        TextView textView = convertView.findViewById(R.id.tv_add_to_list);
        textView.setText(mList.get(position).getmName());
        return convertView;
    }

    @Override
    public int getPosition(@Nullable TaskType item) {
        for (int i = 0; i < mList.size();i++){
            if (item.getmName().equals(mList.get(i).getmName())){
                return i;
            }
        }
        return 11111;
    }
}
