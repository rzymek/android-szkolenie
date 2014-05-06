package org.rzymek.todoexpert;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userEdit;
	private EditText passEdit;
	private Button doLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userEdit = (EditText) findViewById(R.id.user);
		passEdit = (EditText) findViewById(R.id.pass);
		doLogin = (Button) findViewById(R.id.doLogin);

		doLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tryToLogin();
			}
		});
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
		login(user,pass);
	}

	private void login(String user, String pass) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
