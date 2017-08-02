package com.hosiluan.reminder.view.viewMainActivity.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hosiluan.reminder.Item.TaskType;
import com.hosiluan.reminder.R;

import java.util.ArrayList;

/**
 * Created by HoSiLuan on 7/3/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<TaskType> {

    private Context mContext;
    private ArrayList<TaskType> mListNames;

    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TaskType> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListNames = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_default_item, parent, false);

        TextView listName = convertView.findViewById(R.id.tv_list_name_default);

        TaskType item = new TaskType(mListNames.get(position).getmName());
        listName.setText(item.getmName());
        listName.setTypeface(null, Typeface.BOLD);

        return convertView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_item, parent, false);
        TaskType item;

        TextView listName = convertView.findViewById(R.id.tv_list_name);
        item = new TaskType(mListNames.get(position).getmName());
        listName.setText(item.getmName());

        ImageView icon = convertView.findViewById(R.id.img_icon);
        icon.setImageResource(R.drawable.ic_todo);

        if (position == 0 || position == mListNames.size() - 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
            layoutParams.setMargins(15,
                    layoutParams.topMargin,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            icon.setLayoutParams(layoutParams);

            if (position == 0) {
                icon.setImageResource(R.drawable.ic_home);
            } else {
                icon.setImageResource(R.drawable.ic_add_new_list);
            }
        }
        return convertView;
    }

    @Override
    public int getPosition(@Nullable TaskType item) {
        for (int i = 0; i < mListNames.size(); i++) {
            if (item.getmName().equals(mListNames.get(i).getmName())) {
                return i;
            }
        }
        return super.getPosition(item);
    }
}
