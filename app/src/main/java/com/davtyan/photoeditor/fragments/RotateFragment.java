package com.davtyan.photoeditor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.view.MySurfaceView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RotateFragment extends BaseEditFragment implements SeekBar.OnSeekBarChangeListener {


    private SeekBar seekBar;
    private ImageView imageRotate;
    private MySurfaceView mySurfaceView;

    public RotateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rotate, container, false);
        EditorActivity.EXTRA = "draw_sticker";


        mySurfaceView = (getActivity().findViewById(R.id.my_dragView));
        imageRotate = view.findViewById(R.id.rotate_btn);
        imageRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySurfaceView.setRotate(mySurfaceView.getRotate() + 90);
            }
        });
        seekBar = view.findViewById(R.id.rotate_bar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress(0);

        return view;
    }

    public static RotateFragment newInstance() {
        return new RotateFragment();
    }

    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_ROTATE;
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mySurfaceView.setRotate(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
