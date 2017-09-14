package com.hosiluan.reminder.view.viewMainActivity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hosiluan.reminder.R;
import com.hosiluan.reminder.view.viewMainActivity.MainActivity;
import com.hosiluan.reminder.view.viewMainActivity.fragment.DefaultToolBarFragment;

import static com.hosiluan.reminder.view.viewMainActivity.MainActivity.DEFAULT_TOOLBAR_FRAGMENT;
import static com.hosiluan.reminder.view.viewMainActivity.fragment.DefaultToolBarFragment.SEARCH_BAR_FRAGMENT;


/**
 * Created by HoSiLuan on 7/14/2017.
 */

public class SearchBarFragment extends Fragment implements View.OnClickListener {

    private ImageButton mImgBtnBack;
    private EditText mEdtSearch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bar, container, false);
        mImgBtnBack = view.findViewById(R.id.img_btn_back);
        mEdtSearch = view.findViewById(R.id.edt_search);
        mImgBtnBack.setOnClickListener(this);

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                ((MainActivity) getActivity()).findTaskItem(String.valueOf(charSequence));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 0) {
                    ((MainActivity) getActivity()).findTaskItem(String.valueOf(charSequence));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    ((MainActivity) getActivity()).mPresenterMainActivity.loadTaskList();
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_back: {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                getFragmentManager().popBackStack(DEFAULT_TOOLBAR_FRAGMENT, 0);
                fragmentTransaction.commit();
                break;
            }
        }
    }


}
