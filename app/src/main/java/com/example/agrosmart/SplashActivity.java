package com.example.agrosmart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.splash_progress_bar);

        getSupportActionBar().hide();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork()
    {
        for (int i = 0; i < 100; i++)
        {
            try
            {
                Thread.sleep(20);
                mProgress.setProgress(i);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startApp()
    {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}