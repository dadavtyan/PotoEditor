package com.davtyan.photoeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.davtyan.photoeditor.Select;
import com.davtyan.photoeditor.activitys.EditorActivity;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static String TEXT = "";
    private String text;
    private Paint paint;
    private DrawThread drawThread;
    private float x , y, width, height, rotate;


    private float rotateX, rotateY;
    private Bitmap bitmap, bitmapResult;
    private int bitmapWidth, bitmapHeight;
    private float moveX = 0;
    private float moveY = 0;
    private RectF rectF;
    private Bitmap sticker;

    private Select select = Select.DEFAULT;




    public MySurfaceView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public MySurfaceView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        initData();
    }

    private void initData() {
        getHolder().addCallback(this);
        paint = new Paint();
        rectF = new RectF();
        width = getWidth()/2;
        height = getHeight()/2;
    }

    public void setBitmap(Bitmap bitmap) {
        //if (bitmap != null) {
        Log.i("bitmap0","bitmapWidth: " + bitmapWidth + " bitmapHeight: " + bitmapHeight + " - w: " + getWidth() + " h: " + getHeight());
            this.bitmap = bitmap;
            if (bitmap.getWidth() > this.getWidth()){
                bitmapWidth = this.getWidth();
                bitmapHeight = getBitmapHeight(bitmap);
                Log.i("bitmap1","bitmapWidth: " + bitmapWidth + " bitmapHeight: " + bitmapHeight + " - w: " + this.getWidth() + " h: " + this.getHeight());
                x = 5;
                y = (this.getHeight() - bitmapHeight)/2;
            }else if (bitmap.getHeight() > this.getHeight()){
                bitmapHeight = this.getHeight();
                bitmapWidth = getBitmapWidth(bitmap);
                Log.i("bitmap2","bitmapWidth: " + bitmapWidth + " bitmapHeight: " + bitmapHeight + " - w: " + this.getWidth() + " h: " + this.getHeight());
                x = (this.getWidth() - bitmapWidth)/2;
                y = 5;
            }else {
                bitmapWidth = bitmap.getWidth();
                bitmapHeight = bitmap.getHeight();
                Log.i("bitmap","bitmapWidth: " + bitmapWidth + " bitmapHeight: " + bitmapHeight + " - w: " + this.getWidth() + " h: " + this.getHeight());
                x = (this.getWidth() - bitmapWidth)/2;
                y = (this.getHeight() - bitmapHeight)/2;
            }
        //}
    }

    private int getBitmapHeight(Bitmap bitmap) {
        return bitmap.getHeight()*this.getWidth()/bitmap.getWidth();
    }
    private int getBitmapWidth(Bitmap bitmap) {
        return this.getHeight()* bitmap.getWidth()/bitmap.getHeight();
    }

    public void setBitmapResult(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
        }

    }

    public void setBitmapSticker(Bitmap bitmap) {
        if (bitmap != null) {
            sticker = bitmap;
        }

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float move;
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                moveY = event.getY();
                selectEdge(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (EditorActivity.EXTRA == "select_image") {
                    if (select == Select.LEFT) {
                        move = moveX - event.getX();
                        int bitW = bitmapWidth;
                        bitmapWidth += (int) (move);
                        bitmapHeight = bitmapHeight*bitmapWidth/bitW;
                        x -= move / 2;
                        y -= move / 2;
                        moveX = event.getX();
                    } else if (select == Select.RIGHT) {
                        move = moveX - event.getX();
                        int bitW = bitmapWidth;
                        bitmapWidth -= (int) (move);
                        bitmapHeight = bitmapHeight*bitmapWidth/bitW;
                        x += move / 2;
                        y += move / 2;
                        moveX = event.getX();
                    } else if (select == Select.CENTER) {
                        x -= moveX - event.getX();
                        y -= moveY - event.getY();
                        moveX = event.getX();
                        moveY = event.getY();
                    } else if (select == Select.DEFAULT) {
                        rotate += (event.getX() - moveX) / 200;
                    }
                }else {
                    x -= moveX - event.getX();
                    y -= moveY - event.getY();
                    moveX = event.getX();
                    moveY = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                moveX = 0;
                moveY = 0;
                break;
        }
        return true;
    }

    private void selectEdge(MotionEvent event) {

        if (event.getX() > x && event.getX() < x + 85 && event.getY() > y && event.getY() < y + 85) {
            select = Select.LEFT;
        } else if (event.getX() > x + bitmapWidth - 85 && event.getX() < x + bitmapWidth && event.getY() > y && event.getY() < y + 85) {
            select = Select.RIGHT;
        } else if (event.getX() > x && event.getX() < x + 85 && event.getY() > y + bitmapHeight - 85 && event.getY() < y + bitmapHeight) {
            select = Select.LEFT;
        } else if (event.getX() > x + bitmapWidth - 85 && event.getX() < x + bitmapWidth && event.getY() > y + bitmapHeight - 85 && event.getY() < y + bitmapHeight) {
            select = Select.RIGHT;
        } else if (event.getX() > x + 85 && event.getX() < x + bitmapWidth - 85 && event.getY() > y + 85 && event.getY() < y + bitmapHeight - 85) {
            select = Select.CENTER;
        } else {
            select = Select.DEFAULT;
        }
    }

    private int getImageX(MotionEvent event) {
        int length = (int) (Math.pow((event.getX() - moveX), 2) - Math.pow((event.getY() - moveY), 2));
        return (int) Math.sqrt(length);
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setText(String text) {
        this.text = text;
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
            while (running) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.GRAY);
                    onDrawFigure(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }


        private void onDrawFigure(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            rotateX = x + (width - x) / 2;
            rotateY = y + (height - y) / 2;

            switch (EditorActivity.EXTRA) {
                case "draw_point":
                    canvas.rotate(rotate, x, y + 5);
                    canvas.drawPoint(x, y, paint);
                    break;

                case "draw_sticker":
                    drawImage(canvas,(getWidth() - bitmapWidth)/2,(getHeight() - bitmapHeight)/2);
                    canvas.rotate(rotate, x + 15, y + 15);
                    drawSticker(canvas);
                    break;
                case "draw_line":
                    canvas.rotate(rotate, rotateX, rotateY);
                    canvas.drawLine(x, y, width, height, paint);
                    break;
                case "draw_circle":
                    canvas.rotate(rotate, x, y);
                    canvas.drawCircle(x, y, 100, paint);
                    canvas.drawLine(x, y, x + 150, height, paint);
                    break;
                case "draw_rect":
                    canvas.rotate(rotate, rotateX, rotateY);
                    canvas.drawRect(x, y, width, height, paint);
                    break;
                case "draw_text":
                    drawImage(canvas,(getWidth() - bitmapWidth)/2,(getHeight() - bitmapHeight)/2);
                    drawText(canvas);
                    break;
                case "select_image":
                    drawRect(canvas);
                    drawImage(canvas,x,y);
                    break;
            }


        }

        private void drawText(Canvas canvas) {
            paint.setTextSize(150);
            //canvas.rotate(rotate, x + 200 + TEXT.length() * 25, bitmapHeight/2 + 75);
            canvas.drawText(text, x + 200, y + 300, paint);
        }

        private void drawRect(Canvas canvas) {
            if (select == Select.LEFT || select == Select.RIGHT || select == Select.CENTER) {
                rectF = new RectF(x - 10, y - 10, x + bitmapWidth + 10, y + bitmapHeight + 10);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(rectF, paint);
            }
        }

        private void drawImage(Canvas canvas,float cX,float cY) {
            bitmapResult = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
            canvas.drawBitmap(bitmapResult, cX, cY, null);
        }

        private void drawSticker(Canvas canvas) {
            canvas.drawBitmap(sticker,x + 200, y + 300, null);
        }
    }
}
