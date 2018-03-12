package com.davtyan.photoeditor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyFragment extends BaseEditFragment {


    public BeautyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beauty, container, false);
    }

    public static BeautyFragment newInstance() {
        return new BeautyFragment();
    }

    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_BEAUTY;
    }

    @Override
    public void backToMain() {
        //activity.mode = EditorActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();

    }

    public void applyBeauty() {
        backToMain();
    }
}
