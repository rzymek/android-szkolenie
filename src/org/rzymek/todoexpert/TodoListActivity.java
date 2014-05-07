package org.rzymek.todoexpert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rzymek.todoexpert.login.LoginActivity;

import pl.allegro.todo.utils.HttpUtils;
import pl.allegro.todo.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListActivity extends Activity {

	public static final String TAG = "TAG";
	private static final int REQUEST_CODE = 123;
	private ListView list;
	private ArrayAdapter<Todo> listAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!Utils.getLoginManager(this).isLoggedIn()) {
			showLogin();
			return;
		}
		setContentView(R.layout.activity_todo_list);
		list = (ListView) findViewById(R.id.todoItems);
		listAdapter = new ArrayAdapter<Todo>(this, R.layout.todo_item, R.id.todoText) {
			@Override
			public View getView(int idx, View convertView, ViewGroup parent) {
				View view = super.getView(idx, convertView, parent);
				Todo item = getItem(idx);
				CheckBox box = (CheckBox) view.findViewById(R.id.todoCheckbox);
				box.setChecked(item.completed);
				EditText text = (EditText) view.findViewById(R.id.todoText);
				text.setText(item.text);
				view.setBackgroundResource(idx % 2 == 0 ? android.R.color.darker_gray : android.R.color.white);
				return view;
			}
		};
		list.setAdapter(listAdapter);
		refresh();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_menu, menu);
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

			SharedPreferences store = PreferenceManager.getDefaultSharedPreferences(this);
			Editor edit = store.edit();
			edit.clear();
			edit.apply();

			Builder builder = new AlertDialog.Builder(TodoListActivity.this);
			builder.setTitle(R.string.confirm);
			builder.setNegativeButton(android.R.string.no, null);
			builder.setPositiveButton(android.R.string.yes, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					showLogin();
				}
			});
			builder.create().show();
			return true;
		}
		case R.id.action_refresh: {
			refresh();
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

	private void refresh() {
		String token = Utils.getLoginManager(this).token;
		new AsyncTask<String, Void, List<Todo>>() {
			private Exception lastError;

			protected void onPreExecute() {
				lastError = null;
			}

			@Override
			protected List<Todo> doInBackground(String... params) {
				try {
					String tasks = HttpUtils.getTasks(params[0]);
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
				if (result == null) {
					Utils.toast(TodoListActivity.this, "Error: " + lastError);
					List<Todo> empty = Collections.emptyList();
					setItems(empty);
					return;
				}
				setItems(result);
			}
		}.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, token);
	}

	private void showLogin() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	private void setItems(List<Todo> result) {
		listAdapter.clear();
		listAdapter.addAll(result);
	}
}
