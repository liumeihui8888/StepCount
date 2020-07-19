package com.imooc.stepapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.v210.frame.BaseActivity;


public class WelcomeActivity extends BaseActivity
{
    protected void onInitVariable()
    {

    }

    private Runnable jumpToMain = new Runnable()
    {
        public void run()
        {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }
    };

    Handler handler = new Handler();

    protected void onInitView(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_welcome);
        handler.postDelayed(jumpToMain, 3000);
//		handler.postAtTime();
    }


    protected void onRequestData()
    {

    }


    protected void onLoadData()
    {

    }


    protected void onUnLoadData()
    {

    }
}
