package com.nd.jisou.activity;

import com.nd.jisou.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;



public class BaseActionBarActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.banner));
	}
	
	@Override
	public boolean onNavigateUp() {
		finish();
		return super.onNavigateUp();
	}
	


	
}
