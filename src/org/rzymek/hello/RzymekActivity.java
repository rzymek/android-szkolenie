package org.rzymek.hello;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.debug.hv.ViewServer;

public class RzymekActivity extends Activity {

	private static final String TAG = RzymekActivity.class.getSimpleName();
	protected int counter=1;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("counter", counter);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rzymek);
		final Button button = (Button) findViewById(R.id.button1);

		if(savedInstanceState != null) {
			counter = savedInstanceState.getInt("counter");
		}
		button.setText(""+counter);
		
		button.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
//				startActivity(new Intent(RzymekActivity.this, SecondActivity.class));
				button.setText(""+(counter++));
			}
		});
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
