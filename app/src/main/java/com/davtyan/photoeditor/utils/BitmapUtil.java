package com.davtyan.photoeditor.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

    public static Bitmap createTextBitmap(String text, int color) {
        Bitmap bitmap = Bitmap.createBitmap(text.length() * 80, 200, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        if (color != 0) {
            paint.setColor(color);
        } else {
            paint.setColor(Color.WHITE);
        }
        paint.setTextSize(150);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, 150, paint);
        return bitmap;
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


    public static Bitmap getCroppedBitmap(Bitmap src, Path path) {
        Bitmap output = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        canvas.drawPath(path, paint);
        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        canvas.drawBitmap(src, 0, 0, paint);
        return output;
    }

    public static Bitmap getSampledBitmap(String filePath, float reqWidth, float reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
        // Raw height and width of image
        final float height = options.outHeight;
        final float width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final float halfHeight = (float) (height / 1.5);
            final float halfWidth = (float) (width / 1.5);

//             Calculate the largest inSampleSize value that is a power of 2 and keeps both
//             height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;

            }
        }
        Log.d("int_log", String.valueOf(inSampleSize));
        return inSampleSize;
    }
}
