package org.rzymek.todoexpert;

import org.rzymek.todoexpert.login.LoginManager;

import android.app.Application;

public class TodoApp extends Application {
	private LoginManager loginManager;

	@Override
	public void onCreate() {
		super.onCreate();
		loginManager = new LoginManager(this);
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}
}
