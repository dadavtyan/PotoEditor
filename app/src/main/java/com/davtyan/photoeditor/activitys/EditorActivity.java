package com.davtyan.photoeditor.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.davtyan.photoeditor.BitmapManager;
import com.davtyan.photoeditor.CustomViewPager;
import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.fragments.AddTextFragment;
import com.davtyan.photoeditor.fragments.EditTextFragment;
import com.davtyan.photoeditor.fragments.BeautyFragment;
import com.davtyan.photoeditor.fragments.CropFragment;
import com.davtyan.photoeditor.fragments.FilterListFragment;
import com.davtyan.photoeditor.fragments.MainMenuFragment;
import com.davtyan.photoeditor.fragments.PaintFragment;
import com.davtyan.photoeditor.fragments.RotateFragment;
import com.davtyan.photoeditor.fragments.StickerFragment;
import com.davtyan.photoeditor.utils.BitmapUtil;
import com.davtyan.photoeditor.utils.FileUtils;
import com.davtyan.photoeditor.view.MySurfaceView;
import com.davtyan.photoeditor.view.PaintDrawView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_RESULT = 2;
    public static String EXTRA;
    public MySurfaceView mySurfaceView;
    public PaintDrawView paintDrawView;
    private BitmapManager bitmapManager;


    public int mode = 0;
    public CustomViewPager bottomGallery;
    public BottomGalleryAdapter mBottomGalleryAdapter;
    public ViewFlipper flipper;

    //fragments
    public MainMenuFragment menuFragment;
    public StickerFragment stickerFragment;
    public FilterListFragment filterListFragment;
    public EditTextFragment addTextFragment;
    public CropFragment cropFragment;
    public RotateFragment rotateFragment;
    public PaintFragment paintFragment;
    public BeautyFragment beautyFragment;

    private String str;
    private Bitmap bitmap;
    private Uri photoURI;
    private String path;


    public static Intent getJumpIntent(Context context) {
        Intent intent = new Intent(context, EditorActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initView();
        initFragment();
        initData();
    }

    private void initData() {
        EXTRA = "";
        bitmapManager = new BitmapManager();
        str = getIntent().getStringExtra("image");
        if (str != null) {
            getBitmap("file://" + str);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void initFragment() {
        mBottomGalleryAdapter = new BottomGalleryAdapter(this.getSupportFragmentManager());
        bottomGallery.setAdapter(mBottomGalleryAdapter);

        menuFragment = MainMenuFragment.newInstance();
        filterListFragment = FilterListFragment.newInstance();
        cropFragment = CropFragment.newInstance();
        paintFragment = PaintFragment.newInstance();
        stickerFragment = StickerFragment.newInstance();

    }


    private void getBitmap(String str) {
        //Uri url = Uri.fromFile(new File(str));
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(str).getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && str != null) {
            mySurfaceView.setBitmap(bitmap);
            filterListFragment.setBitmap(bitmap);
            EXTRA = "select_image";
            str = null;
        }
    }


    private void initView() {
        bottomGallery = findViewById(R.id.bottom_gallery);
        mySurfaceView = findViewById(R.id.my_dragView);
        paintDrawView = findViewById(R.id.paint_dragView);
        flipper = findViewById(R.id.flipper);

        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.imgUndo).setOnClickListener(this);
        findViewById(R.id.imgRedo).setOnClickListener(this);
        findViewById(R.id.apply).setOnClickListener(this);
        findViewById(R.id.save_btn).setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case MainMenuFragment.INDEX_STICKER:
                //bitmapManager.onBack(mySurfaceView, mySurfaceView.getCountList());
                stickerFragment.backToMain();
                break;
            case MainMenuFragment.INDEX_FILTER:
                filterListFragment.backToMain();
             //   mySurfaceView.setBitmap(filterListFragment.getBitmap());
                return;
            case MainMenuFragment.INDEX_CROP:
                cropFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_ROTATE:
                rotateFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_ADDTEXT:
               // bitmapManager.onBack(mySurfaceView, mySurfaceView.getCountList());
                addTextFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_PAINT:
                paintFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_BEAUTY:
                beautyFragment.backToMain();
                return;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_RESULT:
                    if (photoURI != null) {
                        path = photoURI.getPath();
                        bitmap = BitmapUtil.getSampledBitmap(path, mySurfaceView.getWidth() / 2, mySurfaceView.getHeight() / 2);
                        mySurfaceView.setBitmap(bitmap);
                        filterListFragment.setBitmap(bitmap);
                        EXTRA = "select_image";
                    }
                    break;
            }
        } else {
            Toast.makeText(this, "Image not loaded.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = FileUtils.genEditFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_RESULT);
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                startActivity(new Intent(EditorActivity.this, LoadImageActivity.class));
                break;
            case R.id.imgUndo:
                if (mode == MainMenuFragment.INDEX_MAIN){
                    bitmapManager.undoMain(mySurfaceView);
                    mySurfaceView.backCoordinate();
                }else if (mode == MainMenuFragment.INDEX_PAINT){
                    paintDrawView.undo();
                }
                else {
                    bitmapManager.undo(mySurfaceView);

                }

                break;
            case R.id.imgRedo:
                if (mode == MainMenuFragment.INDEX_MAIN){
                    bitmapManager.redoMain(mySurfaceView);
                    mySurfaceView.backCoordinate();
                }
                else {
                    bitmapManager.redo(mySurfaceView);
                }

                break;
            case R.id.save_btn:
                saveData();
                break;
            case R.id.apply:
                applyBtnClick();
                break;
        }
    }


    public final class BottomGalleryAdapter extends FragmentPagerAdapter {
        public BottomGalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            switch (index) {
                case MainMenuFragment.INDEX_MAIN:
                    // flipper.showPrevious();
                    return menuFragment;
                case MainMenuFragment.INDEX_STICKER:
                    stickerFragment = StickerFragment.newInstance();
                    // flipper.showNext();
                    return stickerFragment;
                case MainMenuFragment.INDEX_FILTER:
                    // flipper.showNext();
                    return filterListFragment;
                case MainMenuFragment.INDEX_CROP:
                    //cropFragment.setBitmap(bitmap);
                    //  flipper.showNext();
                    return cropFragment;
                case MainMenuFragment.INDEX_ROTATE:
                    rotateFragment = RotateFragment.newInstance();
                    // flipper.showNext();
                    return rotateFragment;
                case MainMenuFragment.INDEX_ADDTEXT:
                    addTextFragment = EditTextFragment.newInstance();
                    // flipper.showNext();
                    return addTextFragment;
                case MainMenuFragment.INDEX_PAINT:
                    // flipper.showNext();
                    return paintFragment;
                case MainMenuFragment.INDEX_BEAUTY:
                    beautyFragment = BeautyFragment.newInstance();
                    //  flipper.showNext();
                    return beautyFragment;
            }
            return MainMenuFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 8;
        }
    }


    private void applyBtnClick() {
        switch (mode) {
            case MainMenuFragment.INDEX_STICKER:
                stickerFragment.applyStickers();
                break;
            case MainMenuFragment.INDEX_FILTER:
                filterListFragment.applyFilterImage();
                break;
            case MainMenuFragment.INDEX_CROP:
                cropFragment.applyCropImage();
                break;
            case MainMenuFragment.INDEX_ROTATE:
                rotateFragment.applyRotateImage();
                break;
            case MainMenuFragment.INDEX_ADDTEXT:
                addTextFragment.applyTextImage();
                break;
            case MainMenuFragment.INDEX_PAINT:
                paintFragment.savePaintImage();
                break;
            case MainMenuFragment.INDEX_BEAUTY:
                beautyFragment.applyBeauty();
                break;
        }
    }


    private void saveData() {
        File filename;
        Bitmap bitmapMaster = mySurfaceView.getMainBitmaps().get(mySurfaceView.getMainBitmaps().size() - 1);
        Canvas canvasMaster = new Canvas(bitmapMaster);
        canvasMaster.drawBitmap(bitmapMaster, 0, 0, null);
        try {
            String path = Environment.getExternalStorageDirectory().toString();

            new File(path + "/folder/subfolder").mkdirs();
            filename = new File(path + "/folder/subfolder/image.jpg");

            FileOutputStream out = new FileOutputStream(filename);
            bitmapMaster.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Log.i("bip3", String.valueOf(bitmapMaster.getConfig()));
            out.flush();
            out.close();

            MediaStore.Images.Media.insertImage(getContentResolver(), filename.getAbsolutePath(), filename.getName(), filename.getName());
            Toast.makeText(EditorActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
