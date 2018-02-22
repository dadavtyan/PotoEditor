package com.davtyan.photoeditor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.holders.StickerHolder;


public class StickerAdapter extends RecyclerView.Adapter<StickerHolder> {
    private Context context;
    private int[] image;
    private float left, top;
    private float prvX, prvY;
    private Bitmap bitmapItem;


    public StickerAdapter(Context context, int[] image) {
        this.context = context;
        this.image = image;


    }

    @Override
    public StickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_icon, parent, false);
        StickerHolder viewHolder = new StickerHolder(view,bitmapItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StickerHolder holder, final int position) {
        holder.initData(context,image[position]);
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapItem = BitmapFactory.decodeResource(context.getResources(), image[position]);
                EditorActivity.EXTRA =  "draw_sticker";
            }
        });
    }

    @Override
    public int getItemCount() {
        return image.length;
    }
}