package org.rzymek.todoexpert;

import org.rzymek.todoexpert.login.LoginManager;

import pl.allegro.todo.dao.TodoDao;
import android.app.Application;

public class TodoApp extends Application {
	private LoginManager loginManager;
	public TodoDao dao;

	@Override
	public void onCreate() {
		super.onCreate();
		loginManager = new LoginManager(this);
		dao = new TodoDao(this);
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}
}
