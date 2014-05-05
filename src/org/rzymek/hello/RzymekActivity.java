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
		setContentView(button);
	}

	public void rzClick(View view) {
		Log.d(TAG, "clikc " + new Date());
		Context ctx = this;
		Intent intent = new Intent(ctx, SecondActivity.class);
		startActivity(intent);
	}
}
