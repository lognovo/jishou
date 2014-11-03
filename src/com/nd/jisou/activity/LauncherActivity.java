package com.nd.jisou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nd.jisou.R;

public class LauncherActivity extends BaseActivity {

	private static final long DELAY_START_MAIN = 2000;
	private Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        startMainActivity();
    }

	private void startMainActivity() {
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				Intent newIntent=new Intent(LauncherActivity.this, MainActivity.class);
				startActivity(newIntent);
				LauncherActivity.this.finish();
			}}, DELAY_START_MAIN);
	}


    
}
