package com.matsuhiro.android.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class SystemOverlayLinearLayout extends LinearLayout {
	//private String TAG = "SystemOverlayLinearLayout";
	WindowManager.LayoutParams mWMParams;
	private Context mContext;
	private float mFirstX, mFirstY;
	
	public SystemOverlayLinearLayout(Context context) {
		super(context);
		mContext = context;
	}
	public SystemOverlayLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	
	public void addWindow() {
		mWMParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
				WindowManager.LayoutParams.FLAG_FULLSCREEN |
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.addView(getRootView(), mWMParams);
	}
	
	public void removeWindow() {
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.removeView(getRootView());
	}
	
	@Override
	public boolean dispatchTouchEvent (MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mFirstX = ev.getX();
			mFirstY = ev.getY();
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			mWMParams.x += (int)ev.getX()-mFirstX;
			mWMParams.y += (int)ev.getY()-mFirstY;
			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			wm.updateViewLayout(getRootView(), mWMParams);
		}
		return super.dispatchTouchEvent(ev);
	}
}
