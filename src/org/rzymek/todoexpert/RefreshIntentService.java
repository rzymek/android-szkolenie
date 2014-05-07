package org.rzymek.todoexpert;

import org.json.JSONArray;
import org.json.JSONObject;

import pl.allegro.todo.dao.Todo;
import pl.allegro.todo.utils.HttpUtils;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
		
		long createdAt = app.dao.getLatestCreatedAtTime(app.getLoginManager().userId);
		int newItems = 0;
		try {
			String token = app.getLoginManager().token;
			String tasks = HttpUtils.getTasks(token);
			JSONObject result = new JSONObject(tasks);
			JSONArray results = result.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject object = (JSONObject) results.get(i);
				Todo todo = Todo.fromJsonObject(object);
				Log.i(TAG, ""+todo);
				if(todo.createdAt.getTime() > createdAt) {
					newItems++;
				}
				app.dao.insertOrUpdate(todo);
			}
			sendBroadcast(new Intent(REFRESH));
			notifyOfNew(newItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notifyOfNew(int newItems) {
		String msg = "New items: "+newItems;
		NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
		b.setAutoCancel(true);
		b.setContentText("New TODOs");
		b.setContentText(msg);
		b.setSmallIcon(R.drawable.ic_launcher);
		b.setContentIntent(PendingIntent.getActivity(this, 1,
				new Intent(this, TodoListActivity.class)
				, PendingIntent.FLAG_CANCEL_CURRENT));
		service.notify(1, b.build());
//		service.notify(id, notification);
	 	
	}

}
