package pl.allegro.todo.utils;

import org.rzymek.todoexpert.TodoApp;
import org.rzymek.todoexpert.login.LoginManager;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Utils {
	public static void toast(Context ctx, String s) {
		Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
	}

	public static LoginManager getLoginManager(Activity activity) {
		TodoApp app = (TodoApp) activity.getApplication();
		return app.getLoginManager();
	}
}