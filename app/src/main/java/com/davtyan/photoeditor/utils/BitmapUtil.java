package com.davtyan.photoeditor.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
        Random mRandom = new Random();
        Bitmap bitmap = Bitmap.createBitmap(text.length() * 80, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        if (color != 0) {
            paint.setColor(color);
        } else {
            paint.setColor(Color.WHITE);
        }
        paint.setTextSize(150);
      //  paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        Typeface typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC);
        paint.setTypeface(typeface);
        EmbossMaskFilter filter = new EmbossMaskFilter(
                new float[]{1,5,1}, // direction of the light source
                0.5f, // ambient light between 0 to 1
                10, // specular highlights
                7.5f // blur before applying lighting
        );
        paint.setMaskFilter(filter);
        Rect rectangle = new Rect();
        paint.getTextBounds(
                text, // text
                0, // start
                text.length(), // end
                rectangle // bounds
        );
        RadialGradient gradient = new RadialGradient(
                mRandom.nextInt(rectangle.right),
                mRandom.nextInt(rectangle.bottom),
                mRandom.nextInt(rectangle.right),
                new int[]{
                        getRandomHSVColor(mRandom),
                        getRandomHSVColor(mRandom),
                        getRandomHSVColor(mRandom),
                        getRandomHSVColor(mRandom),
                        getRandomHSVColor(mRandom)
                },
                null, // Stops position is undefined
                Shader.TileMode.MIRROR // Shader tiling mode
        );
        paint.setShader(gradient);
        canvas.drawText(
                text, // Text to draw
                canvas.getWidth()/2, // x
                canvas.getHeight()/2 + Math.abs(rectangle.height())/2, // y
                paint // Paint
        );


       // canvas.drawText(text, 0, 150, paint);
        return bitmap;
    }
    protected static int getRandomHSVColor(Random mRandom){
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);

        // We make the color depth full
        float saturation = 1.0f;

        // We make a full bright color
        float value = 1.0f;

        // We avoid color transparency
        int alpha = 255;

        // Finally, generate the color
        int color = Color.HSVToColor(alpha,new float[]{hue,saturation,value});

        // Return the color
        return color;
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

    public static Bitmap createShadowBitmap(Bitmap originalBitmap, float[] targetColor, int target) {
//        ColorMatrix mColorMatrix = new ColorMatrix(targetColor);
      //  ColorFilter mFilter = new ColorMatrixColorFilter(mColorMatrix);

        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColorFilter(new PorterDuffColorFilter(target, PorterDuff.Mode.OVERLAY));
        //mPaint.setColorFilter(mFilter);
        Bitmap shadowImage32 = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(shadowImage32);
        canvas.drawBitmap(originalBitmap, new Matrix(), mPaint);
        return shadowImage32;
    }

    public static Bitmap blendingBitmap(Bitmap bitmap1,Bitmap bitmap2){
        Bitmap result = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap1, 0, 0, null);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        paint.setShader(new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        canvas.drawRect(0, 0, bitmap2.getWidth(), bitmap2.getHeight(), paint);
        return result;
    }

    public static Bitmap blendedBitmap(Bitmap source, Bitmap layer, float alpha) {
        Bitmap base = source.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap blend = layer.copy(Bitmap.Config.ARGB_8888, false);

        IntBuffer buffBase = IntBuffer.allocate(base.getWidth() * base.getHeight());
        base.copyPixelsToBuffer(buffBase);
        buffBase.rewind();

        IntBuffer buffBlend = IntBuffer.allocate(blend.getWidth() * blend.getHeight());
        blend.copyPixelsToBuffer(buffBlend);
        buffBlend.rewind();

        IntBuffer buffOut = IntBuffer.allocate(base.getWidth() * base.getHeight());
        buffOut.rewind();

        while (buffOut.position() < buffOut.limit()) {
            int filterInt = buffBlend.get();
            int srcInt = buffBase.get();

            int redValueFilter = Color.red(filterInt);
            int greenValueFilter = Color.green(filterInt);
            int blueValueFilter = Color.blue(filterInt);

            int redValueSrc = Color.red(srcInt);
            int greenValueSrc = Color.green(srcInt);
            int blueValueSrc = Color.blue(srcInt);

            int redValueFinal = hardlight(redValueFilter, redValueSrc);
            int greenValueFinal = hardlight(greenValueFilter, greenValueSrc);
            int blueValueFinal = hardlight(blueValueFilter, blueValueSrc);

            int pixel = Color.argb(255, redValueFinal, greenValueFinal, blueValueFinal);

            buffOut.put(pixel);
        }

        buffOut.rewind();

        base.copyPixelsFromBuffer(buffOut);
        blend.recycle();

        return base;
    }

    private static int hardlight(int in1, int in2) {
        float image = (float)in2;
        float mask = (float)in1;
        return ((int)((image < 128) ? (2 * mask * image / 255):(255 - 2 * (255 - mask) * (255 - image) / 255)));

    }
}
