package com.me.jiang.mynews.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.me.jiang.mynews.R;

/**
 * Created by Administrator on 2016/5/6.
 */
public class LauncherActivity extends BaseActivity {


  private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }


}
