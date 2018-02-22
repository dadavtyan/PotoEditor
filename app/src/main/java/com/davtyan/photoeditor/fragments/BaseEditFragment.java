package com.davtyan.photoeditor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.davtyan.photoeditor.activitys.EditorActivity;

public abstract class BaseEditFragment extends Fragment {
    protected EditorActivity activity;

    protected EditorActivity ensureEditActivity(){
        if(activity==null){
            activity = (EditorActivity) getActivity();
        }
        return activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ensureEditActivity();
    }

    public abstract void onShow();

    public abstract void backToMain();
}