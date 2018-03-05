package com.davtyan.photoeditor.adapter.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davtyan.photoeditor.FilterModel;
import com.davtyan.photoeditor.R;

import de.hdodenhof.circleimageview.CircleImageView;


public  class FilterHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;
//de.hdodenhof.circleimageview.CircleImageView
    public FilterHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.my_filter);
        textView = itemView.findViewById(R.id.filter_name);
    }

    public void initData(FilterModel model) {
        imageView.setImageBitmap(model.getBitmap());
        textView.setText(model.getName());

    }
}