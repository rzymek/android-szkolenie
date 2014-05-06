package org.rzymek.todoexpert

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast

class Utils {
	def static toast(Context ctx, String s) {
		Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
	}
	def static sleep(int sec){
		Thread.sleep(sec*1000);
	}
}

class LoginTask extends AsyncTask<String, Integer, Boolean> {
	val LoginActivity parent;

	new(LoginActivity parent) {
		this.parent = parent;
	}

	protected override onPreExecute() {
		parent.doLogin.enabled = false
	}

	protected override doInBackground(String... params) {
		for (i : 0 ..< 5) {
			publishProgress(i)
			Utils.sleep(1)
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
	}
}
