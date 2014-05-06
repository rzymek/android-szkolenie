package org.rzymek.todoexpert

import android.content.Intent
import android.os.AsyncTask

class LoginTask extends AsyncTask<String, Integer, Boolean> {
	val LoginActivity parent;

	new(LoginActivity parent) {
		this.parent = parent;
	}

	protected override onPreExecute() {
		parent.doLogin.enabled = false
	}

	protected override doInBackground(String... params) {
		val n = 100
		parent.progress.max = n
		for (i : 0 ..< n) {
			publishProgress(i)
			Thread.sleep(10)
		} 
		var user = params.get(0)
		var pass = params.get(0)
		return ('test' == user && 'test' == pass)
	}

	protected override onProgressUpdate(Integer... values) {
		parent.progress.progress = values.get(0)
	}

	protected override onPostExecute(Boolean result) {
		Utils.toast(parent, 'Login ' + (if(result) 'OK' else 'FAILED'))
		parent.doLogin.enabled = true
		if(result) {
			val intent = new Intent(parent.applicationContext, TodoListActivity);
			parent.startActivity(intent);
			parent.finish
		}
	}
}