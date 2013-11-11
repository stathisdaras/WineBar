package com.soulmagnet.winebar;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;

public class IntroSplash extends Activity
{

	MediaPlayer wineSong ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_splash);
		
		wineSong = MediaPlayer.create(IntroSplash.this, R.raw.intro);
		wineSong.start();
		
		Thread splash = new Thread(){
			
			public void run(){
				
				try
				{
					sleep(3500);
					
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				} finally{
					
					Intent startMain = new Intent(IntroSplash.this, WineBarMainActivity.class);
					startActivity(startMain);
					
					
				}
				}
				
			};
			
		splash.start();
	
	
	
	
}

	@Override
	protected void onPause()
	{
		super.onPause();	
		wineSong.release();
		finish();
	}
}
