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


public class PhotoHolder extends RecyclerView.ViewHolder {
    TextView folder, imageCount;
    ImageView imageView;

    public PhotoHolder(View view) {
        super(view);
        folder = view.findViewById(R.id.folder);
        imageCount = view.findViewById(R.id.image_count);
        imageView = view.findViewById(R.id.image);
    }

    public void initData(Context context,ImageModel model) {
        folder.setText(model.getStrFolder());
        imageCount.setText(model.getAlImagePath().size() + " ");

        Glide.with(context).load("file://" + model.getAlImagePath().get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

    }

    public ImageView getImageView() {
        return imageView;
    }
}
