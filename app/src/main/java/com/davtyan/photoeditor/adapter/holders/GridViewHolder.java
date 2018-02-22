package com.davtyan.photoeditor.adapter.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davtyan.photoeditor.ImageModel;
import com.davtyan.photoeditor.R;


public class GridViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public GridViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.image_gallery);
    }

    public void initData(Context context, String url) {
            Glide.with(context).load("file://" + url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);

    }

    public ImageView getImageView() {
        return imageView;
    }
}
