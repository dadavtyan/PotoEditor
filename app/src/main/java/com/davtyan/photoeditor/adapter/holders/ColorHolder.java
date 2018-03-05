package com.davtyan.photoeditor.adapter.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.davtyan.photoeditor.R;

public class ColorHolder extends RecyclerView.ViewHolder {
    private View colorPickerView;

    public ColorHolder(View itemView) {
        super(itemView);
        colorPickerView = itemView.findViewById(R.id.color_picker_view);
    }

    public void initData(Integer color) {
        colorPickerView.setBackgroundColor(color);
    }

    public View getColorPickerView() {
        return colorPickerView;
    }
}