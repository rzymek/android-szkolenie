package org.rzymek.hello;

import java.util.Date;

import com.android.debug.hv.ViewServer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class RzymekActivity extends Activity {

	private static final String TAG = RzymekActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_rzymek);
		Button button = new Button(this);
		button.setText("OK...");
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(RzymekActivity.this, SecondActivity.class));
			}
		});
		LinearLayout linearLayout = new LinearLayout(this);
		Button b2 = new Button(this);
		b2.setText("b22");
		linearLayout.addView(button);
		linearLayout.addView(b2);
		setContentView(linearLayout);
		
		ViewServer.get(this).addWindow(this);
		Log.d(TAG,"onCreate");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	public void rzClick(View view) {
		Log.d(TAG, "clikc " + new Date());
		Context ctx = this;
		Intent intent = new Intent(ctx, SecondActivity.class);
		startActivity(intent);
	}
}
