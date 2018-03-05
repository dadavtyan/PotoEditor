package com.davtyan.photoeditor;


import android.graphics.Bitmap;

import com.davtyan.photoeditor.view.MySurfaceView;

import java.util.ArrayList;
import java.util.List;

public class BitmapManager {
    private List<Bitmap> prevBitmapList;
    private List<Bitmap> nextBitmapList = new ArrayList<>();

    public void onBack(MySurfaceView mySurfaceView, int count) {
        prevBitmapList = mySurfaceView.getBitmaps();
        if (prevBitmapList.size() != 0 && prevBitmapList != null) {
            for (int i = 0; i < count; i++) {
                nextBitmapList.add(prevBitmapList.get(prevBitmapList.size() - 1));
                prevBitmapList.remove(prevBitmapList.get(prevBitmapList.size() - 1));
                mySurfaceView.setBitmaps(prevBitmapList);
            }
            mySurfaceView.setCountList(0);
        }
    }

    public void undo(MySurfaceView mySurfaceView) {
        prevBitmapList = mySurfaceView.getBitmaps();
        if (prevBitmapList.size() != 0 && prevBitmapList != null) {
            nextBitmapList.add(prevBitmapList.get(prevBitmapList.size() - 1));
            prevBitmapList.remove(prevBitmapList.get(prevBitmapList.size() - 1));
            mySurfaceView.setBitmaps(prevBitmapList);
            mySurfaceView.setCountList(mySurfaceView.getCountList() - 1);
        }


    }

    public void redo(MySurfaceView mySurfaceView) {
        if (nextBitmapList.size() != 0) {
            prevBitmapList.add(nextBitmapList.get(nextBitmapList.size() - 1));
            nextBitmapList.remove(nextBitmapList.get(nextBitmapList.size() - 1));
            mySurfaceView.setBitmaps(prevBitmapList);
            mySurfaceView.setCountList(mySurfaceView.getCountList() + 1);
        }
    }
}
