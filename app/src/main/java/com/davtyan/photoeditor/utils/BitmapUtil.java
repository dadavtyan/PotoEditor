package com.davtyan.photoeditor.utils;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class BitmapUtil {

    public static Bitmap getBitmapFilter(Bitmap bitmap, int mul, int add) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Color.RED);
        ColorFilter filter = new LightingColorFilter(mul, add);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return bitmap;
    }

    public static Bitmap getColorBitmap(Bitmap bitmap) {
//        int width, height;
//
//        Bitmap bmOut = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), bitmap.getConfig());
//        height = bmOut.getHeight();
//        width =  bmOut.getWidth();
//        int orangeFilter = new Color().rgb(255, 165, 0);
//        int maroonFilter = new Color().rgb(139, 0, 0);
//
//        for (int j = 0; j < height - 1; j++) {
//            for (int i = 0; i < width / 2 - 1; i++) {
//                int newColor = (int) ((double) (bmOut.getPixel(i, j) * 0.3) + ((double) (orangeFilter * 0.7)));
//
//                bmOut.setPixel(i, j, newColor);
//            }
//        }
//
//        for (int j = 0; j < height - 1; j++) {
//            for (int i = width / 2; i < width - 1; i++) {
//                double newColor = (bmOut.getPixel(i, j) * 0.3 + maroonFilter * 0.7);
//                bmOut.setPixel(i, j, (int) newColor);
//            }
//        }
//        return bmOut;
        Bitmap bmOut = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());

        int[] allPixels = new int[bitmap.getHeight() * bitmap.getWidth()];


        int position = bitmap.getWidth() * bitmap.getHeight() / 60;
        int thicknessLine = bitmap.getWidth() * bitmap.getHeight() / 100;

        bitmap.getPixels(allPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int positionN = position * 4;
        int positionHorizontal = bitmap.getWidth() / 40;

        for (int i = 0; i < allPixels.length; i++) {

            if (i < position && i > position - thicknessLine) {
                allPixels[i] = Color.BLACK;
            } else if (i < positionN && i > positionN - thicknessLine) {
                allPixels[i] = Color.BLACK;
            } else if (i == positionN) {
                positionN += position * 4;
            }

            if (i < positionHorizontal && i > positionHorizontal - bitmap.getWidth() / 80) {
                allPixels[i] = Color.BLACK;
            } else if (i == positionHorizontal) {
                positionHorizontal += bitmap.getWidth() / 40;
                //  Log.i("positionN","i = " + i);
            }
        }

        bmOut.setPixels(allPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return bmOut;


    }


    public static Bitmap fffff(Bitmap bitmap) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        Bitmap bitmapFilter;
        for (int i = 0; i < 4; i++) {
            bitmapFilter = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
            switch (i) {
                case 0:
                    bitmaps.add(bitmapFilter);
                    break;
                case 1:
                    bitmaps.add(getBitmapFilter(bitmapFilter, 0xFF1493, 0x00000000));
                    break;
                case 2:
                    bitmaps.add(getBitmapFilter(bitmapFilter, 0x00FF00, 0x00000000));
                    break;
                case 3:
                    bitmaps.add(getBitmapFilter(bitmapFilter, 0x6a1b9a, 0x00000000));
                    break;
            }
        }
        return combineImageIntoOneFlexWidth(bitmaps, bitmap.getWidth(), bitmap.getHeight());
    }

    private static Bitmap combineImageIntoOneFlexWidth(ArrayList<Bitmap> bitmap, int width, int height) {
//        int w = 0, h = 0;
//        for (int i = 0; i < bitmap.size(); i++) {
//            if (i < bitmap.size() - 1) {
//                h = bitmap.get(i).getHeight() > bitmap.get(i + 1).getHeight() ? bitmap.get(i).getHeight() : bitmap.get(i + 1).getHeight();
//            }
//            w += bitmap.get(i).getWidth();
//        }

        Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        int lift = 0;
        int top = 0;
        for (int i = 0; i < bitmap.size(); i++) {
            Log.e("HTML", "Combine: " + i + "/" + bitmap.size() + 1);
            switch (i) {
                case 0:
                    lift = 0;
                    top = 0;
                    break;
                case 1:
                    lift = bitmap.get(i).getWidth();
                    top = 0;
                    break;
                case 2:
                    lift = 0;
                    top = bitmap.get(i).getHeight();
                    break;
                case 3:
                    lift = bitmap.get(i).getWidth();
                    top = bitmap.get(i).getHeight();
                    break;
            }

          // lift = (i == 0 ? 0 : lift + bitmap.get(i).getWidth());
            //attributes 1:bitmap,2:width that starts drawing,3:height that starts drawing
            canvas.drawBitmap(bitmap.get(i), lift, top, null);
        }
        return temp;
    }

}
