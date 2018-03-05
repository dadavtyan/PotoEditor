package com.davtyan.photoeditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.FilterModel;
import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.holders.FilterHolder;

import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {

    private EditorActivity context;
    private List<FilterModel> filterModels;

    public FilterAdapter(EditorActivity context, List<FilterModel> filterModels) {
        this.context = context;
        this.filterModels = filterModels;

    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list, parent, false);
        FilterHolder viewHolder = new FilterHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterHolder holder, final int position) {
        holder.initData(filterModels.get(position));
    }

    @Override
    public int getItemCount() {
        return filterModels.size();
    }


}
