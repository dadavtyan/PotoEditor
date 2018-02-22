package com.davtyan.photoeditor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Parsing {

    public static byte[] parsBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (bitmap != null) bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return bytes;
    }

    public static Bitmap parsByteArray(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public String encodeToBase64(Bitmap image) {
        byte[] bytes = parsBitmap(image);
        String imageEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imageEncoded;
    }

    public Bitmap decodeBase64(String input) {
       byte[] bytes = Base64.decode(input, 0);
        return parsByteArray(bytes);
    }
}