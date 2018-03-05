package com.davtyan.photoeditor.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.adapter.GridViewAdapter;

import java.io.File;


public class PhotosActivity extends AppCompatActivity {
    private int position;
    private GridViewAdapter adapter;
    private RecyclerView recyclerViewImage;
    private RecyclerView.LayoutManager layoutManager;
    private File photoFile;
    private TextView btnCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        recyclerViewImage = findViewById(R.id.folder_list);
        btnCamera = findViewById(R.id.camera_btn);
        photoFile = new File(getExternalFilesDir("img"), "scan.jpg");

        position = getIntent().getIntExtra("value", 5);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(layoutManager);

        adapter = new GridViewAdapter(this, LoadImageActivity.alImages.get(position).getAlImagePath());
        recyclerViewImage.setAdapter(adapter);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(EditorActivity.getJumpIntent(PhotosActivity.this, false, photoFile), 100);
            }
        });

    }

}

