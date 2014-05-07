package org.rzymek.todoexpert;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pl.allegro.todo.utils.HttpUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TodoListActivity extends Activity {

	public static final String TAG = "TAG";
	private static final int REQUEST_CODE = 123;
	private String token;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_menu, menu);
		token = getIntent().getStringExtra("token");
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			Utils.toast(this, "Canceled");
			return;
		}
		if (requestCode == REQUEST_CODE) {
			Todo result = (Todo) data.getExtras().get("result");
			Utils.toast(this, "Result:" + result);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout: {
			Builder builder = new AlertDialog.Builder(TodoListActivity.this);
			builder.setTitle(R.string.confirm);
			builder.setNegativeButton(android.R.string.no, null);
			builder.setPositiveButton(android.R.string.yes, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(TodoListActivity.this, LoginActivity.class));
					finish();
				}
			});
			builder.create().show();
			return true;
		}
		case R.id.action_refresh: {
			new AsyncTask<Void, Void, List<Todo>>() {
				private Exception lastError;

				@Override
				protected List<Todo> doInBackground(Void... params) {
					lastError = null;
					try {
						String tasks = HttpUtils.getTasks(token);
						JSONObject result = new JSONObject(tasks);
						JSONArray results = result.getJSONArray("results");
						List<Todo> ret = new ArrayList<>(results.length());
						for (int i = 0; i < results.length(); i++) {
							JSONObject object = (JSONObject) results.get(i);
							Todo todo = new Todo(object.getString("content"), object.getBoolean("done"));
							ret.add(todo);
						}
						return ret;
					} catch (Exception e) {
						e.printStackTrace();
						lastError = e;
						return null;
					}
				}

				@Override
				protected void onPostExecute(List<Todo> result) {
					if (result != null) {
						Utils.toast(TodoListActivity.this, "Received: " + result);
					} else {
						Utils.toast(TodoListActivity.this, "Error: " + lastError);
					}
				}
			}.execute();
			return true;
		}
		case R.id.action_add: {
			Intent intent = new Intent(getApplicationContext(), AddActivity.class);
			intent.putExtra(TAG, "hello");
			startActivityForResult(intent, REQUEST_CODE);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
