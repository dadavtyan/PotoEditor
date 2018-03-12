package com.davtyan.photoeditor.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.RecyclerItemClickListener;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.ColorPickerAdapter;
import com.davtyan.photoeditor.model.ColorList;
import com.davtyan.photoeditor.view.MySurfaceView;
import com.davtyan.photoeditor.view.PaintDrawView;

import java.util.ArrayList;
import java.util.List;

public class PaintFragment extends BaseEditFragment {

    private Bitmap paintBitmap;
    private PaintDrawView paintDrawView;
    private RecyclerView addPaintColors;
    private LinearLayoutManager layoutManager;
    private List<Integer> colorPickerColors;
    private ColorList colorList;
    private MySurfaceView mySurfaceView;

    public PaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paint, container, false);
        paintDrawView = (getActivity().findViewById(R.id.paint_dragView));
        mySurfaceView = (getActivity().findViewById(R.id.my_dragView));
        addPaintColors = view.findViewById(R.id.color_picker_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addPaintColors.setLayoutManager(layoutManager);

        colorList = new ColorList();

        getDefaultColors(getContext());
        addPaintColors.setAdapter(new ColorPickerAdapter(getActivity(), colorPickerColors));

        addPaintColors.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                       paintDrawView.setColor(colorPickerColors.get(position));
                    }
                })

        );
        return view;
    }

    public static PaintFragment newInstance() {
        return new PaintFragment();
    }

    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_PAINT;
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
        activity.paintDrawView.setVisibility(View.GONE);
        activity.mySurfaceView.setVisibility(View.VISIBLE);
        EditorActivity.EXTRA = "select_image";
    }

    public void savePaintImage() {
        mySurfaceView.setBitmap(paintDrawView.getBitmap());
        backToMain();
    }

    public void setBitmap(Bitmap bitmap) {
        paintBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    public List<Integer> getDefaultColors(Context context) {
        colorPickerColors = new ArrayList<>();
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
        return colorPickerColors;
    }


}
