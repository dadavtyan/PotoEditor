package com.davtyan.photoeditor.adapter.holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;


public class StickerHolder extends RecyclerView.ViewHolder{
    private ImageView imageView;
    private float left,top;
    private Context context;
    private Bitmap bitmapItem;

    public StickerHolder(View itemView,Bitmap bitmapItem) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_icon);
        this.bitmapItem = bitmapItem;
    }

    public void initData(Context context,int model) {
        imageView.setImageResource(model);
        this.context = context;

    }

    public ImageView getImageView() {
        return imageView;
    }

}