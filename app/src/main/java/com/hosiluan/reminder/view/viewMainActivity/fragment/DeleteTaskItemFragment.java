package com.hosiluan.reminder.view.viewMainActivity.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hosiluan.reminder.R;
import com.hosiluan.reminder.view.viewMainActivity.listener.DeleteTaskItemFragmentListener;

import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.DEFAULT_TOOLBAR_FRAGMENT;

/**
 * Created by HoSiLuan on 7/20/2017.
 */

public class DeleteTaskItemFragment extends Fragment implements View.OnClickListener {


    private ImageButton imgBtnBack, imgBtnDelete;
    private TextView tvAmountOfSelectedItem;
    private DeleteTaskItemFragmentListener deleteTaskItemFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        deleteTaskItemFragmentListener = (DeleteTaskItemFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_task_item, container, false);

        imgBtnBack = view.findViewById(R.id.img_btn_back_delete_fragment);
        imgBtnDelete = view.findViewById(R.id.img_btn_delete_delete_fragment);
        tvAmountOfSelectedItem = view.findViewById(R.id.tv_amount_of_selected_item);
        imgBtnBack.setOnClickListener(this);
        imgBtnDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_back_delete_fragment: {
                backToDefaultFragment();
                break;
            }
            case R.id.img_btn_delete_delete_fragment: {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to delete those tasks?")
                        .setTitle("Delete")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                backToDefaultFragment();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTaskItemFragmentListener.onDeleteTaskItemFragmentDelete();
                            }
                        });

                alertDialog = builder.create();
                alertDialog.show();
                break;
            }
        }
    }

    public void backToDefaultFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack(DEFAULT_TOOLBAR_FRAGMENT, 0);
        fragmentTransaction.commit();
        deleteTaskItemFragmentListener.onDeleteTaskItemFragmentBack();
    }
}
