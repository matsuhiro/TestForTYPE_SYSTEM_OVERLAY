package com.matsuhiro.android.TestForTYPE_SYSTEM_OVERLAY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestForTYPE_SYSTEM_OVERLAY extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button start_btn = (Button)findViewById(R.id.start_service);
		start_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(TestForTYPE_SYSTEM_OVERLAY.this, SystemOverlayLinearLayoutService.class);
				startService(intent);
			}
			
		});
		Button stop_btn = (Button)findViewById(R.id.stop_service);
		stop_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(new Intent(TestForTYPE_SYSTEM_OVERLAY.this, SystemOverlayLinearLayoutService.class));
				
			}
			
		});
	}
}