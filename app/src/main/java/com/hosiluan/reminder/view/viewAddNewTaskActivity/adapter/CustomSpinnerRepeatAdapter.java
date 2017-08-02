package com.hosiluan.reminder.view.viewAddNewTaskActivity.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.hosiluan.reminder.R;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/6/2017.
 */

public class CustomSpinnerRepeatAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mList;

    public CustomSpinnerRepeatAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mList = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_repeat, parent, false);
        TextView tvRepeatType = convertView.findViewById(R.id.tv_repeat_type);
        tvRepeatType.setText(mList.get(position));

        return convertView;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_repeat_default, parent, false);
        TextView tvRepeatType = convertView.findViewById(R.id.tv_repeat_type_default);
        tvRepeatType.setText(mList.get(position));

        return convertView;
    }

}
