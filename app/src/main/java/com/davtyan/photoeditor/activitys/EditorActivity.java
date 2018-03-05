package com.davtyan.photoeditor.activitys;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_FROM_ALBUM = "extra_from_album";
    private static final String EXTRA_CROPPED_FILE = "extra_cropped_file";

    private static final int GALLERY_RESULT = 1;
    private static final int CAMERA_RESULT = 2;
    public static String EXTRA;
    public MySurfaceView mySurfaceView;
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


    public static Intent getJumpIntent(Context context, boolean fromAlbum, File croppedFile) {
        Intent intent = new Intent(context, EditorActivity.class);
//        intent.putExtra(EXTRA_FROM_ALBUM, fromAlbum);
//        intent.putExtra(EXTRA_CROPPED_FILE, croppedFile);
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
            cropFragment.setBitmap(bitmap);
            EXTRA = "select_image";
            str = null;
        }
    }


    private void initView() {

        bottomGallery = findViewById(R.id.bottom_gallery);
        mySurfaceView = findViewById(R.id.my_dragView);
        flipper = findViewById(R.id.flipper);

        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.imgUndo).setOnClickListener(this);
        findViewById(R.id.imgRedo).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case MainMenuFragment.INDEX_STICKER:
                bitmapManager.onBack(mySurfaceView, mySurfaceView.getCountList());
                stickerFragment.backToMain();
                break;
            case MainMenuFragment.INDEX_FILTER:
                filterListFragment.backToMain();
                mySurfaceView.setBitmap(filterListFragment.getBitmap());
                return;
            case MainMenuFragment.INDEX_CROP:
                cropFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_ROTATE:
                rotateFragment.backToMain();
                return;
            case MainMenuFragment.INDEX_ADDTEXT:
                bitmapManager.onBack(mySurfaceView, mySurfaceView.getCountList());
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
                bitmapManager.undo(mySurfaceView);
                break;
            case R.id.imgRedo:
                bitmapManager.redo(mySurfaceView);
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
                    bitmap = mySurfaceView.getBitmap();
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
                    paintFragment = PaintFragment.newInstance();
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

}
