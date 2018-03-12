package com.davtyan.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.davtyan.photoeditor.Position;
import com.davtyan.photoeditor.Select;
import com.davtyan.photoeditor.activitys.EditorActivity;

import java.util.ArrayList;
import java.util.List;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private List<String> texts;
    private Paint paint;
    private DrawThread drawThread;
    private float rotate;

    private List<Position> positionList;
    private Position position;
    private int posSelected;
    private boolean selected;
    private int viewX, viewY;

    private Bitmap bitmap;
    private int bitmapWidth, bitmapHeight;
    private float moveX = 0;
    private float moveY = 0;
    private RectF rectF;
    private Bitmap sticker;
    private List<Bitmap> currentBitmaps;
    private List<Bitmap> mainBitmaps;


    private Select select = Select.DEFAULT;
    private int bitmapX, bitmapY;

    private float rectX, rectY, rectWidth, rectHeight;


    public MySurfaceView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public MySurfaceView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        initData();
    }

    private void initData() {
        currentBitmaps = new ArrayList<>();
        mainBitmaps = new ArrayList<>();
        texts = new ArrayList<>();
        positionList = new ArrayList<>();
        getHolder().addCallback(this);
        paint = new Paint();
        rectF = new RectF();
    }


    public void setBitmap(Bitmap bitmap) {
        setBitmapData(bitmap);
    }

    public void setFilter(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private void setBitmapData(Bitmap bitmap) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            bitmapWidth = getWidth() - 50;
            bitmapHeight = getBitmapSize(bitmap.getHeight(), bitmapWidth, bitmap.getWidth());
            bitmapX = 25;
            bitmapY = (this.getHeight() - bitmapHeight) / 2;
        } else if (bitmap.getHeight() > bitmap.getWidth()) {
            bitmapHeight = getHeight() - 50;
            bitmapWidth = getBitmapSize(bitmap.getWidth(), bitmapHeight, bitmap.getHeight());
            bitmapX = (getWidth() - bitmapWidth) / 2;
            bitmapY = 25;
        } else if (bitmap.getHeight() == bitmap.getWidth()) {
            bitmapWidth = getWidth() - 10;
            bitmapHeight = bitmapWidth;
            bitmapX = (getWidth() - bitmapWidth) / 2;
            bitmapY = (getHeight() - bitmapHeight) / 2;
        }
        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
        mainBitmaps.add(this.bitmap);
        setRectData();
    }

    private void setRectData() {
        rectX = bitmapX;
        rectY = bitmapHeight / 8 + bitmapY;
        rectWidth = rectX + bitmapWidth;
        rectHeight = 7 * bitmapHeight / 8 + bitmapY;

//        rectX = bitmapWidth/8 + bitmapX;
//        rectY =  bitmapY;
//        rectWidth = 7*bitmapWidth/8 + bitmapX;
//        rectHeight = bitmapHeight + rectY;

//        rectX = bitmapWidth / 8 + bitmapX;
//        rectY = bitmapHeight / 8 + bitmapY;
//        rectWidth = 7 * bitmapWidth / 8 + bitmapX;
//        rectHeight = 7 * bitmapHeight / 8 + bitmapY;
    }

    private int getBitmapSize(int size1, int size2, int size3) {
        return (size1 * size2) / size3;
    }


    public void addBitmapSticker() {
        Bitmap newBitmap = Bitmap.createScaledBitmap(mainBitmaps.get(mainBitmaps.size() - 1), bitmapWidth, bitmapHeight, true);
        Canvas canvas = new Canvas(newBitmap);
        for (int i = 0; i < currentBitmaps.size(); i++) {
            canvas.drawBitmap(currentBitmaps.get(i), positionList.get(i).getDX() - bitmapX,
                    positionList.get(i).getDY() - bitmapY, null);
        }
        mainBitmaps.add(newBitmap);
        currentBitmaps.clear();
        positionList.clear();
    }

    public void addBitmapFilter() {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
        mainBitmaps.add(newBitmap);
    }

    public void setBitmapSticker(Bitmap bitmap) {
        viewX = 400;
        viewY = 500;
        if (bitmap != null) {
            sticker = bitmap;
            currentBitmaps.add(sticker);
            position = new Position(viewX, viewY);
            positionList.add(position);
        }
    }

    public void backCoordinate() {
        bitmapWidth = mainBitmaps.get(mainBitmaps.size() - 1).getWidth();
        bitmapHeight = mainBitmaps.get(mainBitmaps.size() - 1).getHeight();
        bitmapX = (getWidth() - bitmapWidth) / 2;
        bitmapY = (getHeight() - bitmapHeight) / 2;

    }

    public List<Bitmap> getMainBitmaps() {
        return mainBitmaps;
    }

    public void setMainBitmaps(List<Bitmap> mainBitmaps) {
        this.mainBitmaps = mainBitmaps;
    }


    public List<Bitmap> getBitmaps() {
        return currentBitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.currentBitmaps = bitmaps;
    }

    public void setText(String text) {
        texts.add(text);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }


    public int getRotate() {
        return (int) rotate;
    }

    public float getRectX() {
        return rectX - bitmapX;
    }

    public float getRectY() {
        return rectY - bitmapY;
    }

    public float getRectWidth() {
        return rectWidth - bitmapX;
    }

    public float getRectHeight() {
        return rectHeight - bitmapY;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < currentBitmaps.size(); i++) {
                    Bitmap bitmap = currentBitmaps.get(i);
                    if (event.getX() > positionList.get(i).getDX() && event.getX() < positionList.get(i).getDX() + bitmap.getWidth()
                            && event.getY() > positionList.get(i).getDY() && event.getY() < positionList.get(i).getDY() + bitmap.getHeight()) {
                        posSelected = i;
                        viewX = positionList.get(i).getDX();
                        viewY = positionList.get(i).getDY();
                        selected = true;
                        break;
                    } else {
                        selected = false;
                    }
                }
                moveX = event.getX();
                moveY = event.getY();

                //  selectEdge(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (EditorActivity.EXTRA == "draw_crop") {
//                    float move= 0;
//                    if (select == Select.TOP_RIGHT) {
//                        move = moveX - event.getX();
//                        int bitW = bitmapWidth;
//                        bitmapWidth += (int) (move);
//                        bitmapHeight = bitmapHeight * bitmapWidth / bitW;
//                        x -= move / 2;
//                        y -= move / 2;
//
//                        moveX = event.getX();
//                    } else if (select == Select.TOP_LEFT) {
//                        move = moveX - event.getX();
//                        int bitW = bitmapWidth;
//                        bitmapWidth -= (int) (move);
//                        bitmapHeight = bitmapHeight * bitmapWidth / bitW;
//                        x += move / 2;
//                        y += move / 2;
//                        moveX = event.getX();
//                    } else if (select == Select.CENTER) {
//                        x -= moveX - event.getX();
//                        y -= moveY - event.getY();
//                        moveX = event.getX();
//                        moveY = event.getY();
//                    } else if (select == Select.DEFAULT) {
//                        rotate += (event.getX() - moveX) / 200;
//                    }
//                } else {
//                    x -= moveX - event.getX();
//                    y -= moveY - event.getY();
//                    moveX = event.getX();
//                    moveY = event.getY();
                    if (event.getX() > rectX && event.getX() < rectX + 85 && event.getY() > rectY && event.getY() < rectY + 85) {
                        rectX += event.getX() - moveX;
                        rectY += event.getY() - moveY;
                        moveX = event.getX();
                        moveY = event.getY();
                    } else if (event.getX() > rectWidth - 85 && event.getX() < rectWidth && event.getY() > rectY && event.getY() < rectY + 85) {
                        rectWidth += event.getX() - moveX;
                        rectY += event.getY() - moveY;
                        moveX = event.getX();
                        moveY = event.getY();
                    } else if (event.getX() > rectX && event.getX() < rectX + 85 && event.getY() > rectHeight - 85 && event.getY() < rectHeight) {
                        rectX += event.getX() - moveX;
                        rectHeight += event.getY() - moveY;
                        moveX = event.getX();
                        moveY = event.getY();
                    } else if (event.getX() > rectWidth - 85 && event.getX() < rectWidth && event.getY() > rectHeight - 85 && event.getY() < rectHeight) {
                        rectWidth += event.getX() - moveX;
                        rectHeight += event.getY() - moveY;
                        moveX = event.getX();
                        moveY = event.getY();
                    } else if (event.getX() > rectX + 85 && event.getX() < rectWidth - 85 && event.getY() > rectY + 85 && event.getY() < rectHeight - 85) {
                        rectX += event.getX() - moveX;
                        rectY += event.getY() - moveY;
                        rectWidth += event.getX() - moveX;
                        rectHeight += event.getY() - moveY;
                        moveX = event.getX();
                        moveY = event.getY();
                    } else {
                        select = Select.DEFAULT;

                    }

                } else if (selected && EditorActivity.EXTRA == "draw_sticker") {
                    viewX -= moveX - event.getX();
                    viewY -= moveY - event.getY();
                    moveX = event.getX();
                    moveY = event.getY();
                    positionList.get(posSelected).setDX(viewX);
                    positionList.get(posSelected).setDY(viewY);
                } else if (EditorActivity.EXTRA == "draw_path") {
                    /**
                     *
                     */
                }
                break;
            case MotionEvent.ACTION_UP:
                if (rectX < bitmapX) {
                    rectWidth += (bitmapX - rectX);
                    rectX = bitmapX;
                }
                if (rectY < bitmapY) {
                    rectHeight += (bitmapY - rectY);
                    rectY = bitmapY;
                }
                if (rectWidth > (bitmapWidth + bitmapX)) {
                    rectX -= rectWidth - (bitmapWidth + bitmapX);
                    rectWidth = bitmapWidth + bitmapX;
                }
                if (rectHeight > (bitmapHeight + bitmapY)) {
                    rectY -= rectHeight - (bitmapHeight + bitmapY);
                    rectHeight = bitmapHeight + bitmapY;
                }
                moveX = 0;
                moveY = 0;
                break;
        }
        invalidate();
        return true;
    }

    private void selectEdge(MotionEvent event) {

        if (event.getX() > rectX && event.getX() < rectX + 85 && event.getY() > rectY && event.getY() < rectY + 85) {
            select = Select.TOP_LEFT;
        } else if (event.getX() > rectWidth - 85 && event.getX() < rectWidth && event.getY() > rectY && event.getY() < rectY + 85) {
            select = Select.TOP_RIGHT;
        } else if (event.getX() > rectX && event.getX() < rectX + 85 && event.getY() > rectHeight - 85 && event.getY() < rectHeight) {
            select = Select.BOTTOM_LEFT;
        } else if (event.getX() > rectWidth - 85 && event.getX() < rectWidth && event.getY() > rectHeight - 85 && event.getY() < rectHeight) {
            select = Select.BOTTOM_RIGHT;
        } else if (event.getX() > rectX + 85 && event.getX() < rectWidth - 85 && event.getY() > rectY + 85 && event.getY() < rectHeight - 85) {
            select = Select.CENTER;
        } else {
            select = Select.DEFAULT;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    public class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            Canvas canvas = null;
            while (running ) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.BLACK);
                    onDrawFigure(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }
            running = false;
        }


        private void onDrawFigure(Canvas canvas) {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);

            switch (EditorActivity.EXTRA) {
                case "draw_sticker":
                    canvas.rotate(rotate, getWidth() / 2, getHeight() / 2);
                    drawImage(canvas, bitmapX, bitmapY);
                    drawSticker(canvas);
                    break;
                case "draw_crop":
                    drawImage(canvas, bitmapX, bitmapY);
                    drawRect(canvas);
                    break;
                case "draw_rotate":
                    canvas.rotate(rotate, getWidth() / 2, getHeight() / 2);
                    drawImage(canvas, bitmapX, bitmapY);
                    break;
                case "select_image":
                    drawImage(canvas, bitmapX, bitmapY);
                    break;
                case "draw_filter":
                    drawFilter(canvas, bitmapX, bitmapY);
                    break;
            }


        }

        private void drawFilter(Canvas canvas, int bitmapX, int bitmapY) {
            canvas.drawBitmap(bitmap, bitmapX, bitmapY, null);
        }


        private void drawText(Canvas canvas) {
            paint.setTextSize(150);
            //canvas.rotate(rotate, x + 200 + TEXT.length() * 25, bitmapHeight/2 + 75);
            for (int i = 0; i < texts.size(); i++) {
                canvas.drawText(texts.get(i), bitmapWidth / 2, bitmapHeight / 2, paint);
            }
        }

        private void drawRect(Canvas canvas) {
            Paint paintN = new Paint();
            paintN.setColor(Color.WHITE);
            paintN.setStrokeWidth(4);

            canvas.drawLine(rectX, rectY + (rectHeight - rectY) / 3, rectWidth, rectY + (rectHeight - rectY) / 3, paintN);
            canvas.drawLine(rectX, rectY + 2 * (rectHeight - rectY) / 3, rectWidth, rectY + 2 * (rectHeight - rectY) / 3, paintN);
            canvas.drawLine(rectX + (rectWidth - rectX) / 3, rectY, rectX + (rectWidth - rectX) / 3, rectHeight, paintN);
            canvas.drawLine(rectX + 2 * (rectWidth - rectX) / 3, rectY, rectX + 2 * (rectWidth - rectX) / 3, rectHeight, paintN);

            rectF = new RectF(rectX, rectY, rectWidth, rectHeight);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rectF, paint);

        }

        public Bitmap drawImage(Canvas canvas, float cX, float cY) {
            // bitmapResult = Bitmap.createScaledBitmap(mainBitmaps.get(mainBitmaps.size() - 1), bitmapWidth, bitmapHeight, true);
            canvas.drawBitmap(mainBitmaps.get(mainBitmaps.size() - 1), cX, cY, null);
            return mainBitmaps.get(mainBitmaps.size() - 1);
        }

        private void drawSticker(Canvas canvas) {
            for (int i = 0; i < currentBitmaps.size(); i++) {
                canvas.drawBitmap(currentBitmaps.get(i), positionList.get(i).getDX(), positionList.get(i).getDY(), null);
            }
        }
    }
}
