package com.davtyan.photoeditor.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.davtyan.photoeditor.R;


public class EditTextFragment extends BaseEditFragment implements View.OnClickListener {
    private TextView textView, textColor;
    private ViewFlipper flipper;
    private String text;
    private View view;

    public EditTextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_text, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flipper = view.findViewById(R.id.flipper);

//        flipper.setInAnimation(activity, R.anim.in_bottom_to_top);
//        flipper.setOutAnimation(activity, R.anim.out_bottom_to_top);

        initView(view);
    }

    private void initView(View view) {

        //editText.addTextChangedListener(this);
        textView = view.findViewById(R.id.change_text);
        textView.setOnClickListener(this);

        textColor = view.findViewById(R.id.edit_color);
        textColor.setOnClickListener(this);
    }

    public static EditTextFragment newInstance() {
        return new EditTextFragment();
    }

    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_ADDTEXT;
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_text:
                AddTextFragment.show(activity);
                break;
        }
    }

    public void applyTextImage() {
        activity.mySurfaceView.addBitmapSticker();
        backToMain();
    }
}
