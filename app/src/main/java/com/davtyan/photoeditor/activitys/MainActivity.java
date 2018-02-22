package com.davtyan.photoeditor.activitys;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davtyan.photoeditor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void toMovieInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoadImageActivity.class);
                startActivity(intent);
            }
        }, 700);
    }



    @Override
    protected void onResume() {
        super.onResume();
        toMovieInfo();
    }
}
