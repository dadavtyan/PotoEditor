package com.davtyan.photoeditor.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.davtyan.photoeditor.activitys.EditorActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PaintDrawView extends View {
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private LinkedList<DrawPath> savePath;
    private DrawPath mLastDrawPath;

    private Path mPath;
    private Paint mBitmapPaint, mPaint;
    private float startX, startY, endX, endY;
    private int color = Color.BLUE;
    private int bitmapWidth, bitmapHeight;
    private float bitmapX, bitmapY;
    private List<Bitmap> mainBitmaps;

    public PaintDrawView(Context context) {
        this(context, null);
    }

    public PaintDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        savePath = new LinkedList<>();
        mainBitmaps = new ArrayList<>();
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        EditorActivity.EXTRA = "draw_path";

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, bitmapX, bitmapY, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initializePen();
        endX = event.getX();
        endY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mPath.reset();
                mPath.moveTo(endX, endY);
                startX = endX;
                startY = endY;
                mCanvas.drawPath(mPath, mPaint);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(startX, startY, endX, endY);
                startX = endX;
                startY = endY;
                mCanvas.drawPath(mPath, mPaint);
                break;
            case MotionEvent.ACTION_UP:
                mPath.lineTo(startX, startY);
                mCanvas.drawPath(mPath, mPaint);
                addBitmapFilter();
                mPath = null;
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
    public void addBitmapFilter() {
        Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(), true);
        mainBitmaps.add(newBitmap);
    }
    public void undo() {
        if (mainBitmaps != null && mainBitmaps.size() > 1) {
            mainBitmaps.remove(mainBitmaps.size() - 1);
            mCanvas.drawBitmap(mainBitmaps.get(mainBitmaps.size() - 1), bitmapX, bitmapY, null);
            invalidate();
        }
    }

    public void setBitmap(Bitmap bitmap ,int width,int height) {
        bitmapX = (width - bitmap.getWidth()) / 2;
        bitmapY = (height - bitmap.getHeight()) / 2;
        mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        mCanvas = new Canvas(mBitmap);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void initializePen() {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int color) {
        this.color = color;
    }

    private class DrawPath {
        Path path;
        int paintColor;
        float paintWidth;

        DrawPath(Path path, int paintColor, float paintWidth) {
            this.path = path;
            this.paintColor = paintColor;
            this.paintWidth = paintWidth;
        }

        int getPaintColor() {
            return paintColor;
        }

        float getPaintWidth() {
            return paintWidth;
        }
    }
}
