package com.davtyan.photoeditor;


import android.graphics.Bitmap;
import android.util.Log;

import com.davtyan.photoeditor.view.MySurfaceView;

import java.util.ArrayList;
import java.util.List;

public class BitmapManager {
    private List<Bitmap> prevBitmapList;
    private List<Bitmap> nextBitmapList = new ArrayList<>();
    private List<Bitmap> prevMainBitmapList;
    private List<Bitmap> nextMainBitmapList = new ArrayList<>();
    private List<float[]> cor = new ArrayList<>();
    float [] x;

    public void undo(MySurfaceView mySurfaceView) {
        prevBitmapList = mySurfaceView.getBitmaps();
        if (prevBitmapList.size() != 0 && prevBitmapList != null) {
            nextBitmapList.add(prevBitmapList.get(prevBitmapList.size() - 1));
            prevBitmapList.remove(prevBitmapList.get(prevBitmapList.size() - 1));
            mySurfaceView.setBitmaps(prevBitmapList);
        }
    }

    public void redo(MySurfaceView mySurfaceView) {
        if (nextBitmapList.size() != 0) {
            prevBitmapList.add(nextBitmapList.get(nextBitmapList.size() - 1));
            nextBitmapList.remove(nextBitmapList.get(nextBitmapList.size() - 1));
            mySurfaceView.setBitmaps(prevBitmapList);
        }
    }

    public void undoMain(MySurfaceView mySurfaceView) {
        prevMainBitmapList = mySurfaceView.getMainBitmaps();
        if (prevMainBitmapList.size() > 1 && prevMainBitmapList != null) {
            nextMainBitmapList.add(prevMainBitmapList.get(prevMainBitmapList.size() - 1));
            prevMainBitmapList.remove(prevMainBitmapList.get(prevMainBitmapList.size() - 1));
            mySurfaceView.setMainBitmaps(prevMainBitmapList);
        }

        Log.i("mySurfaceView", " size() = " + mySurfaceView.getMainBitmaps().size());
    }

    public void redoMain(MySurfaceView mySurfaceView) {
        if (nextMainBitmapList.size() != 0) {
            prevMainBitmapList.add(nextMainBitmapList.get(nextMainBitmapList.size() - 1));
            nextMainBitmapList.remove(nextMainBitmapList.get(nextMainBitmapList.size() - 1));
            mySurfaceView.setMainBitmaps(prevMainBitmapList);
        }
    }

}
