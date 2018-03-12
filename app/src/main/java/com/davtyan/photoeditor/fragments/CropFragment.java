package com.davtyan.photoeditor.fragments;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.graphics.PathParser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.utils.BitmapUtil;
import com.davtyan.photoeditor.view.MySurfaceView;

public class CropFragment extends BaseEditFragment implements View.OnClickListener {

    private Bitmap bitmapCrop;
    private MySurfaceView mySurfaceView;

    public CropFragment() {
        // Required empty public constructor
    }

    public static CropFragment newInstance() {
        return new CropFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mySurfaceView = (getActivity().findViewById(R.id.my_dragView));
        view.findViewById(R.id.crop_1).setOnClickListener(this);
        view.findViewById(R.id.crop_2).setOnClickListener(this);
        view.findViewById(R.id.crop_3).setOnClickListener(this);
        view.findViewById(R.id.crop_4).setOnClickListener(this);
        view.findViewById(R.id.crop_5).setOnClickListener(this);
    }


    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_CROP;
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
        EditorActivity.EXTRA = "select_image";
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmapCrop = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crop_1:
                Log.i("bitmapCrop", "bitmapCrop: - " + bitmapCrop);
                mySurfaceView.setBitmap(convertToHeart(bitmapCrop));
                break;
            case R.id.crop_2:
                EditorActivity.EXTRA = "draw_crop";
                break;
            case R.id.crop_3:

                break;
            case R.id.crop_4:
                break;
            case R.id.crop_5:
                break;
        }
    }

    private Bitmap cropBitmap() {
        float targetWidth = (mySurfaceView.getRectWidth() - mySurfaceView.getRectX()  );
        float targetHeight = (mySurfaceView.getRectHeight() - mySurfaceView.getRectY());
        //float width = targetWidth

        Bitmap targetBitmap = Bitmap.createBitmap((int) targetWidth, (int) targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawBitmap(bitmapCrop,
                new Rect(
                        (int) (mySurfaceView.getRectX()),
                        (int) (mySurfaceView.getRectY()),
                        (int) (mySurfaceView.getRectWidth()  ),
                        (int) (mySurfaceView.getRectHeight())),
                new RectF(0, 0, targetWidth, targetHeight), null);
        bitmapCrop = targetBitmap.copy(Bitmap.Config.ARGB_8888, true);

        return targetBitmap;
    }

    @SuppressLint("RestrictedApi")
    private Bitmap convertToHeart(Bitmap src) {
        return BitmapUtil.getCroppedBitmap(src, getHeartPath(src));
    }

    @SuppressLint("RestrictedApi")
    private Path getHeartPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(getString(R.string.heart)),
                src.getWidth(), src.getHeight());
    }


    public static Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }

    public void applyCropImage() {
        mySurfaceView.setBitmap(cropBitmap());
        backToMain();
    }
}



