package com.davtyan.photoeditor.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.davtyan.photoeditor.adapter.PhotoFolderAdapter;
import com.davtyan.photoeditor.ImageModel;
import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.utils.ImageUtil;

import java.util.ArrayList;

public class LoadImageActivity extends AppCompatActivity {


    public static ArrayList<ImageModel> alImages = new ArrayList<>();
    private PhotoFolderAdapter photosFolder;
    private RecyclerView recyclerViewFolder;
    private RecyclerView.LayoutManager layoutManager;
    private static final int REQUEST_PERMISSIONS = 100;
    private ImageUtil imageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        imageUtil = new ImageUtil();
        layoutManager = new GridLayoutManager(this, 2);

        recyclerViewFolder = findViewById(R.id.folder_list);
        recyclerViewFolder.setHasFixedSize(true);
        recyclerViewFolder.setLayoutManager(layoutManager);


        openAlbumWithPermissionsCheck();

    }

    private void openAlbumWithPermissionsCheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS);
            return;
        } else {
            openAlbum();
        }
    }


    public ArrayList<ImageModel> openAlbum() {
        photosFolder = new PhotoFolderAdapter(this, imageUtil.openAlbum(this, alImages));
        recyclerViewFolder.setAdapter(photosFolder);
        return alImages;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        Toast.makeText(LoadImageActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
