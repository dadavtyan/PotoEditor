package com.davtyan.photoeditor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.holders.GridViewHolder;

import java.util.ArrayList;
import java.util.List;


public class GridViewAdapter extends RecyclerView.Adapter<GridViewHolder> {

    private Context context;
    private List<String> strings = new ArrayList<>();

    public GridViewAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_photo, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        holder.initData(context, strings.get(position));

        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorActivity.class);
                intent.putExtra("image",strings.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
}
