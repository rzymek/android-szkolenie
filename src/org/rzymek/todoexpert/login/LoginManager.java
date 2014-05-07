package org.rzymek.todoexpert.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class LoginManager {
	private static final String USER_ID = "userId";
	private static final String TOKEN = "token";
	public String token;
	public String userId;
	private SharedPreferences preferences;

	public LoginManager(Context ctx) {
		preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		token = preferences.getString(TOKEN,null);
		userId= preferences.getString(USER_ID,null);
	}

	public void login(String token, String userId) {
		this.token = token;
		this.userId = userId;
		Editor edit = preferences.edit();
		edit.putString(TOKEN, token);
		edit.putString(USER_ID, userId);
		edit.apply();
	}

	public void logout() {
		token = null;
		userId = null;
		Editor edit = preferences.edit();
		edit.clear();
		edit.apply();
	}

	public boolean isLoggedIn() {
		return token != null && userId != null;
	}
}