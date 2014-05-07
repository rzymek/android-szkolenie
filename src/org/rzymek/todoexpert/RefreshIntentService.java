package org.rzymek.todoexpert;

import org.json.JSONArray;
import org.json.JSONObject;

import pl.allegro.todo.dao.Todo;
import pl.allegro.todo.utils.HttpUtils;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshIntentService extends IntentService {

	public static final String REFRESH = "org.rzymek.todoexpert.REFRESH";
	private static final String TAG = RefreshIntentService.class.getSimpleName();

	public RefreshIntentService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		TodoApp app = (TodoApp) getApplication();
		try {
			String token = app.getLoginManager().token;
			String tasks = HttpUtils.getTasks(token);
			JSONObject result = new JSONObject(tasks);
			JSONArray results = result.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject object = (JSONObject) results.get(i);
				Todo todo = Todo.fromJsonObject(object);
				Log.i(TAG, ""+todo);
				app.dao.insertOrUpdate(todo);
			}
			sendBroadcast(new Intent(REFRESH));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
