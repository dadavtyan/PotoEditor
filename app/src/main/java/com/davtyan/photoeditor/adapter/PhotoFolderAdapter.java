package com.davtyan.photoeditor.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.ImageModel;
import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.LoadImageActivity;
import com.davtyan.photoeditor.activitys.PhotosActivity;
import com.davtyan.photoeditor.adapter.holders.PhotoHolder;

import java.util.List;

public class PhotoFolderAdapter extends RecyclerView.Adapter<PhotoHolder> {

    private LoadImageActivity context;
    private List<ImageModel> imageModels;

    public PhotoFolderAdapter(LoadImageActivity context, List<ImageModel> imageModels) {
        this.context = context;
        this.imageModels = imageModels;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_image_folder, parent, false);
        PhotoHolder viewHolder = new PhotoHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, final int position) {
       holder.initData(context,imageModels.get(position));
       holder.getImageView().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, PhotosActivity.class);
               intent.putExtra("value",position);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }
}
