package com.davtyan.photoeditor.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.adapter.GridViewAdapter;


public class PhotosActivity extends AppCompatActivity {
    private int position;
    private GridViewAdapter adapter;
    private RecyclerView recyclerViewImage;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        recyclerViewImage = findViewById(R.id.folder_list);

        position = getIntent().getIntExtra("value", 5);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(layoutManager);

        adapter = new GridViewAdapter(this, LoadImageActivity.alImages.get(position).getAlImagePath());
        recyclerViewImage.setAdapter(adapter);


    }
}

