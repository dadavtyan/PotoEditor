package com.davtyan.photoeditor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.adapter.holders.ColorHolder;


import java.util.List;


public class ColorPickerAdapter extends RecyclerView.Adapter<ColorHolder> {

    private Context context;
    private List<Integer> colorPickerColors;

    public ColorPickerAdapter(Context context, List<Integer> colorPickerColors) {
        this.context = context;
        this.colorPickerColors = colorPickerColors;
    }


    @Override
    public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_picker_item_list, parent, false);
        ColorHolder viewHolder = new ColorHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ColorHolder holder, int position) {
        holder.initData(colorPickerColors.get(position));
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }


}

