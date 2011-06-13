package com.matsuhiro.android.TestForTYPE_SYSTEM_OVERLAY;

import com.matsuhiro.android.widget.SystemOverlayLinearLayout;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SystemOverlayLinearLayoutService extends Service {
	
	private Context mContext;
	private LinearLayout mLinearLayout;
	private SystemOverlayLinearLayout mLayout;
	private int mWidth;
	private int mHeight;
	private int mTouchCount = 0;
	private int mCount = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onStart(Intent intent, int startId) {
		mContext = (Context)this;
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mWidth = display.getWidth();
		mHeight = display.getHeight();
		
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLinearLayout = (LinearLayout)inflater.inflate(R.layout.systemoverlayout, null);
		
		mLayout = (SystemOverlayLinearLayout)mLinearLayout.findViewById(R.id.overlay);
		
		Button btn = (Button)mLayout.findViewById(R.id.overbutton);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCount = mCount+1;
				TextView text = new TextView(mContext);
				String string = "Clicked:"+mCount;
				text.setText(string);
				text.setLayoutParams(
						new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
				mLayout.addView(text);
				text.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mLayout.removeView(v);
						
					}
				});
				
			}
		});
		btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				
				if (action == MotionEvent.ACTION_DOWN) {
					mTouchCount = mTouchCount+1;
					ViewGroup.LayoutParams params = mLayout.getLayoutParams();
					params.width = mWidth;
					params.height = mHeight;
					mLayout.setLayoutParams(params);
					Log.v("SystemOverlay","down"+mTouchCount);
				} else if (action == MotionEvent.ACTION_UP) {
					mTouchCount = mTouchCount-1;
					ViewGroup.LayoutParams params = mLayout.getLayoutParams();
					params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
					params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
					mLayout.setLayoutParams(params);
				}
				Log.v("SystemOverlay","action="+action);
				return false;
			}
		});
		
		mLayout.addWindow();
	}
	
	@Override
	public void onDestroy() {
		mLayout.removeAllViews();
		mLayout.removeWindow();
		mLayout = null;
	}
}
