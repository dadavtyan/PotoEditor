package com.davtyan.photoeditor.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.FilterModel;
import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.RecyclerItemClickListener;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.FilterAdapter;
import com.davtyan.photoeditor.utils.BitmapUtil;
import com.davtyan.photoeditor.view.MySurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterListFragment extends BaseEditFragment {
    @ColorInt int[] mul = {0xFF7F7F7F,0x29b6f6,0xFFFFFFFF,0x00FFFF,0x00BFFF,0xFF7F7F7F, 0x6a1b9a, 0x7FFFD4,0x9fa8da,0xFF1493,0x00FF00,0xFF7F7F7F};
    @ColorInt int[] add = {0x00000000,0x00000000,0x00222222,0x00000000,0x00000000,0x00222222, 0x00000000, 0x00000000,0x00000000,0x00000000,0x00000000,0x9fa8da};
    String [] name = {"b_1", "b_2", "b_3", "b_4", "b_5", "b_6", "b_7", "b_8", "b_9", "b_10", "b_11", "b_12"};
    private RecyclerView recyclerView;
    private FilterAdapter filterAdapter;
    private List<FilterModel> filterModels;
    private FilterModel model;
    private Bitmap bitmap;
    private Bitmap bitmapFilter;
    private Bitmap bitmapFilterItem;


    public FilterListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_filter_list, container, false);
        LinearLayoutManager horizontalLayoutManage = new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView = view.findViewById(R.id.filter_list);
        recyclerView.setLayoutManager(horizontalLayoutManage);
        if (bitmap != null) {
            filterAdapter = new FilterAdapter(activity, getBitmapList(bitmap));
            recyclerView.setAdapter(filterAdapter);
        }


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                      if (position == 0){
                          ((MySurfaceView)getActivity().findViewById(R.id.my_dragView)).setBitmap(bitmap);
                      }
                      else if (position == filterModels.size() - 1){

//                          LoadImageTask task = new LoadImageTask();
//                          task.execute(bitmap);

                          ((MySurfaceView)getActivity().findViewById(R.id.my_dragView)).
                                  setBitmapResult(BitmapUtil.fffff(bitmap));
                      }
                      else {
                          bitmapFilter = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                          ((MySurfaceView)getActivity().findViewById(R.id.my_dragView)).
                                  setBitmapResult(BitmapUtil.getBitmapFilter(bitmapFilter, mul[position - 1],add[position - 1]));
                          bitmapFilter = null;
                      }
                    }
                })

        );
        return view;
    }

    public static FilterListFragment newInstance() {
        return new FilterListFragment();
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
    }

    private List<FilterModel> getBitmapList(Bitmap bitmap) {

        filterModels = new ArrayList<>();
        bitmapFilterItem = Bitmap.createScaledBitmap(bitmap, 120, 240, true);
        model = new FilterModel(bitmapFilterItem, "b_0");
        filterModels.add(model);

        for (int i = 0; i < 12; i++) {
            bitmapFilterItem = Bitmap.createScaledBitmap(bitmap, 120, 240, true);
            model = new FilterModel(BitmapUtil.getBitmapFilter(bitmapFilterItem,mul[i],add[i]), name[i]);
            filterModels.add(model);
            bitmapFilterItem = null;
        }
        return filterModels;
    }


    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_FILTER;
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public Bitmap getBitmapFilter() {
        return bitmapFilter;
    }


    private final class LoadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            return BitmapUtil.getColorBitmap(params[0]);
        }


    }
}
