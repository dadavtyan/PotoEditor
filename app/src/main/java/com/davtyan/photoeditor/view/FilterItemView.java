package com.davtyan.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;



public class FilterItemView extends View {

    private Bitmap mBitmap;

    public FilterItemView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public FilterItemView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap,0,0,null);
            Log.i("canvas", "width: " + canvas.getWidth() + " height: " + canvas.getHeight());
        }
    }

    public void loadImage(Bitmap bitmap) {
        mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
