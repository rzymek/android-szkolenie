package org.rzymek.todoexpert

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar

class LoginActivity extends Activity {
	protected var EditText userEdit
	protected var EditText passEdit
	protected var Button doLogin
	protected var ProgressBar progress

	protected override onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState)
		contentView = R.layout.activity_login
		userEdit = (findViewById(R.id.user) as EditText)
		passEdit = (findViewById(R.id.pass) as EditText)
		doLogin = (findViewById(R.id.doLogin) as Button)
		progress = (findViewById(R.id.progress) as ProgressBar)
		doLogin.onClickListener = [tryToLogin]
		
		userEdit.text = 'test'
		passEdit.text = 'test'
	}

	protected def tryToLogin() {
		var user = userEdit.text.toString
		var pass = passEdit.text.toString
		var failed = false
		if (TextUtils::isEmpty(user)) {
			userEdit.error = getString(R.string.empty_value)
			failed = true
		}
		if (TextUtils::isEmpty(pass)) {
			passEdit.error = getString(R.string.empty_value)
			failed = true
		}
		if (failed)
			return;
		login(user, pass)
	}

	private def login(String user, String pass) {
		var task = new LoginTask(this)
		task.executeOnExecutor(AsyncTask::SERIAL_EXECUTOR, user, pass)
	}

	protected def toast(String string) {
		Utils::toast(this, string)
	}

	protected static def sleep(int sec) {
		try {
			Thread::sleep(sec * 1000)
		} catch (InterruptedException e) {
			e.printStackTrace
		}
	}

	override onCreateOptionsMenu(Menu menu) {
		menuInflater.inflate(R.menu.login, menu)
		true
	}

	override onOptionsItemSelected(MenuItem item) {
		var id = item.itemId
		if (id === R.id.action_settings) {
			return true;
		}
		super.onOptionsItemSelected(item)
	}
}
