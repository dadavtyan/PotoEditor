package com.davtyan.photoeditor;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;

public class FilterModel {

    private Bitmap bitmap;
    private String name;

    public FilterModel(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
