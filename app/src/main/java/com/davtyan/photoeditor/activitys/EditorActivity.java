package com.davtyan.photoeditor.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.davtyan.photoeditor.view.MySurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EditorActivity extends AppCompatActivity {

    private static final int GALLERY_RESULT = 1;
    public static String EXTRA = "";
    private MySurfaceView mySurfaceView;

    public int mode = 0;
    public CustomViewPager bottomGallery;
    public BottomGalleryAdapter mBottomGalleryAdapter;

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

    private TextView textView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        textView = findViewById(R.id.load_image);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMediaStore();
            }
        });
        initData();
        menuFragment = MainMenuFragment.newInstance();
        filterListFragment = FilterListFragment.newInstance();
        str  =   getIntent().getStringExtra("image");

        getBitmap("file://" + str);
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
            EXTRA = "select_image";
            mySurfaceView.setBitmap(bitmap);
            filterListFragment.setBitmap(bitmap);
            str = null;
        }
    }


    private void initData() {
        bottomGallery = findViewById(R.id.bottom_gallery);
        mySurfaceView = findViewById(R.id.my_dragView);
        mBottomGalleryAdapter = new BottomGalleryAdapter(
                this.getSupportFragmentManager());
        bottomGallery.setAdapter(mBottomGalleryAdapter);
    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case MainMenuFragment.INDEX_STICKER:
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
                case GALLERY_RESULT:
                    Uri selectedImage = data.getData();
                    Log.i("myLog","selectedImage:" + selectedImage);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        if (bitmap != null){
                            EXTRA = "select_image";
                            mySurfaceView.setBitmap(bitmap);
                            filterListFragment.setBitmap(bitmap);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else {
            Toast.makeText(this, "Image not loaded.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMediaStore() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_RESULT);
    }


    public final class BottomGalleryAdapter extends FragmentPagerAdapter {
        public BottomGalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            switch (index) {
                case MainMenuFragment.INDEX_MAIN:
                    return menuFragment;
                case MainMenuFragment.INDEX_STICKER:
                    stickerFragment = StickerFragment.newInstance();
                    return stickerFragment;
                case MainMenuFragment.INDEX_FILTER:
                    bitmap = mySurfaceView.getBitmap();
                    return filterListFragment;
                case MainMenuFragment.INDEX_CROP:
                    cropFragment = CropFragment.newInstance();
                    return cropFragment;
                case MainMenuFragment.INDEX_ROTATE:
                    rotateFragment = RotateFragment.newInstance();
                    return rotateFragment;
                case MainMenuFragment.INDEX_ADDTEXT:
                    addTextFragment = EditTextFragment.newInstance();
                    return addTextFragment;
                case MainMenuFragment.INDEX_PAINT:
                    paintFragment = PaintFragment.newInstance();
                    return paintFragment;
                case MainMenuFragment.INDEX_BEAUTY:
                    beautyFragment = BeautyFragment.newInstance();
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
