package com.davtyan.photoeditor.utils;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.davtyan.photoeditor.ImageModel;
import com.davtyan.photoeditor.adapter.PhotoFolderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageUtil {
    private boolean boolean_folder;
    private Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
    private final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

    public ArrayList<ImageModel> openAlbum(Context context, ArrayList<ImageModel> alImages) {
        alImages.clear();
        int position = 0;
        Cursor cursor;
        int indexData, folderName;

        String absolutePathOfImage = null;
        cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        indexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        folderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(indexData);

            for (int i = 0; i < alImages.size(); i++) {
                if (alImages.get(i).getStrFolder().equals(cursor.getString(folderName))) {
                    boolean_folder = true;
                    position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }

            if (boolean_folder) {
                ArrayList<String> alPath = new ArrayList<>();
                alPath.addAll(alImages.get(position).getAlImagePath());
                alPath.add(absolutePathOfImage);
                alImages.get(position).setAlImagePath(alPath);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                ImageModel obj_model = new ImageModel();
                obj_model.setStrFolder(cursor.getString(folderName));
                obj_model.setAlImagePath(al_path);
                alImages.add(obj_model);
            }
        }
        return alImages;
    }
}
