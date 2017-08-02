package com.hosiluan.reminder.view.viewAddNewTaskActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by HoSiLuan on 7/23/2017.
 */

public class MySpinner extends android.support.v7.widget.AppCompatSpinner {

    int prevPos = -1;

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == getSelectedItemPosition() && prevPos == position) {
            getOnItemSelectedListener().onItemSelected(null, null, position, 0);
        }
        prevPos = position;
    }

}
