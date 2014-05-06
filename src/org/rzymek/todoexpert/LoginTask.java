package org.rzymek.todoexpert;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Integer, Boolean> {
	public LoginActivity parent;

	public LoginTask(LoginActivity parent) {
		this.parent = parent;
	} 

	protected void onPreExecute() {
		parent.doLogin.setEnabled(false);;
	}

	protected Boolean doInBackground(String... params) {
		int n = 100;
		parent.progress.setMax(n);
		for(int i=0;i<n;i++) {
			publishProgress(i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		String user = params[0];
		String pass = params[1];
		Log.d("login",user+":"+pass);
		return true;//("test" == user && "test" == pass);
	}

	protected void onProgressUpdate(Integer... values) {
		parent.progress.setProgress(values[0]);
	}

	protected void onPostExecute(Boolean result) {
		Utils.toast(parent, "Login " + (result ? "OK":"FAILED"));
		parent.doLogin.setEnabled(true);
		if(result) {
			Intent intent = new Intent(parent, TodoListActivity.class);
			parent.startActivity(intent);
			parent.finish();
		}
	}
}