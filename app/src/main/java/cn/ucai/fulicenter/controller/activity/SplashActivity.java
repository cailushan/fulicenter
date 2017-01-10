package cn.ucai.fulicenter.controller.activity;
/**
 * 闪屏界面
 */

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.MainActivity;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.MFGT;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.startActivity(SplashActivity.this, MainActivity.class);

            }
        }, 2000);
    }
}
