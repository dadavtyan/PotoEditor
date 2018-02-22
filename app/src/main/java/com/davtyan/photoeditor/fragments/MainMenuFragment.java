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
public class MainMenuFragment extends BaseEditFragment implements View.OnClickListener {

    public static final int INDEX_MAIN = 0;
    public static final int INDEX_STICKER = 1;
    public static final int INDEX_FILTER = 2;
    public static final int INDEX_CROP = 3;
    public static final int INDEX_ROTATE = 4;
    public static final int INDEX_ADDTEXT = 5;
    public static final int INDEX_PAINT = 6;
    public static final int INDEX_BEAUTY = 7;


    public MainMenuFragment() {
        // Required empty public constructor
    }

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.image_filter).setOnClickListener(this);
        view.findViewById(R.id.sticker_list).setOnClickListener(this);
        view.findViewById(R.id.add_text).setOnClickListener(this);
        view.findViewById(R.id.image_rotate).setOnClickListener(this);
        view.findViewById(R.id.image_paint).setOnClickListener(this);
        view.findViewById(R.id.image_crop).setOnClickListener(this);
        view.findViewById(R.id.beauty).setOnClickListener(this);

    }

    @Override
    public void onShow() {

    }

    @Override
    public void backToMain() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_filter:
                onShowFilterFragment();
                break;
            case R.id.sticker_list:
                onShowStickerFragment();
                break;
            case R.id.add_text:
                onShowAddTextFragment();
                break;
            case R.id.image_rotate:
                onShowRotateFragment();
                break;
            case R.id.image_paint:
                onShowPaintFragment();
                break;
            case R.id.image_crop:
                onShowCropFragment();
                break;
            case R.id.beauty:
                onShowBeautyFragment();
                break;

        }
    }




    private void onShowFilterFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_FILTER);
       // activity.filterListFragment.onShow();
        activity.mode = INDEX_FILTER;
    }

    private void onShowStickerFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_STICKER);
        //activity.stickerFragment.onShow();
        activity.mode = INDEX_STICKER;
    }

    private void onShowAddTextFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_ADDTEXT);
      //  activity.addTextFragment.onShow();
        activity.mode = INDEX_ADDTEXT;

        AddTextFragment.show(activity);

    }
    private void onShowPaintFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_PAINT);
        //activity.paintFragment.onShow();
        activity.mode = INDEX_PAINT;
    }

    private void onShowRotateFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_ROTATE);
        //activity.rotateFragment.onShow();
        activity.mode = INDEX_ROTATE;
    }

    private void onShowCropFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_CROP);
        //activity.cropFragment.onShow();
        activity.mode = INDEX_CROP;
    }

    private void onShowBeautyFragment() {
        activity.bottomGallery.setCurrentItem(INDEX_BEAUTY);
        activity.mode = INDEX_BEAUTY;
        //activity.beautyFragment.onShow();
    }

}