package com.davtyan.photoeditor.fragments;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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


public class FilterListFragment extends BaseEditFragment {

    private RecyclerView recyclerView;
    private FilterAdapter filterAdapter;
    private List<FilterModel> filterModels;
    private MySurfaceView mySurfaceView;
    private FilterModel model;

    private @ColorInt int[] mul ;
    private @ColorInt int[] add ;
    private Bitmap bitmap,bitmapFilterItem;
    private String [] name = {"b_0","b_1", "b_2", "b_3", "b_4", "b_5", "b_6", "b_7", "b_8", "b_9", "b_10", "b_11", "b_12"};

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
        mySurfaceView = (getActivity().findViewById(R.id.my_dragView));

        if (isAdded()){
            Resources res = getResources();
            //mul = res.getIntArray(R.array.mul_filters);
            mul = res.getIntArray(R.array.color_filters);
            add = res.getIntArray(R.array.add_filters);
            filterAdapter = new FilterAdapter(activity, getBitmapList(bitmap));
            recyclerView.setAdapter(filterAdapter);
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                      if (position == 0){
                          mySurfaceView.setFilter(bitmap);
                      }
                      else if (position == filterModels.size() - 1){

//                          LoadImageTask task = new LoadImageTask();
//                          task.execute(bitmap);

                          mySurfaceView.setFilter(BitmapUtil.blendingBitmap(bitmap,BitmapUtil.fffff(bitmap)));

                      }
                      else {
                          mySurfaceView.setFilter(BitmapUtil.createShadowBitmap(bitmap,null, mul[position]));
                      }
                        EditorActivity.EXTRA = "draw_filter";
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
        for (int i = 0; i < 13; i++) {
            bitmapFilterItem = Bitmap.createScaledBitmap(bitmap, 120, 240, true);
            if (i == 0) model = new FilterModel(bitmapFilterItem, name[i]);
            else model = new FilterModel(BitmapUtil.createShadowBitmap(bitmapFilterItem,null,mul[i]), name[i]);
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
        EditorActivity.EXTRA  = "select_image";
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }




    public void applyFilterImage() {
        mySurfaceView.addBitmapFilter();
        backToMain();
    }


    private final class LoadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            return BitmapUtil.getColorBitmap(params[0]);
        }


    }
}
