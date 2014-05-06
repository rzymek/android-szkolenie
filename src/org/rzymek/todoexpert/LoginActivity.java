package org.rzymek.todoexpert;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginActivity extends Activity {
	protected EditText userEdit;
	protected EditText passEdit;
	protected Button doLogin;
	protected ProgressBar progress;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userEdit = (EditText) findViewById(R.id.user);
		passEdit = (EditText) findViewById(R.id.pass);
		doLogin = (Button) findViewById(R.id.doLogin);
		progress = (ProgressBar) findViewById(R.id.progress);
		doLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tryToLogin();
			}
		});

		if (BuildConfig.DEBUG) {
			userEdit.setText("test");
			passEdit.setText("test");
		}
	}

	protected void tryToLogin() {
		String user = userEdit.getText().toString();
		String pass = passEdit.getText().toString();
		boolean failed = false;
		if (TextUtils.isEmpty(user)) {
			userEdit.setError(getString(R.string.empty_value));
			failed = true;
		}
		if (TextUtils.isEmpty(pass)) {
			passEdit.setError(getString(R.string.empty_value));
			failed = true;
		}
		if (failed)
			return;
		login(user, pass);
	}

	private void login(String user, String pass) {
		LoginTask task = new LoginTask(this);
		task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, user, pass);
	}

	protected void toast(String string) {
		Utils.toast(this, string);
	}

	protected static void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
