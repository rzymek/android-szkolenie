package org.rzymek.todoexpert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rzymek.todoexpert.login.LoginActivity;
import org.rzymek.todoexpert.login.LoginManager;

import pl.allegro.todo.dao.Todo;
import pl.allegro.todo.dao.TodoDao;
import pl.allegro.todo.utils.HttpUtils;
import pl.allegro.todo.utils.Utils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TodoListActivity extends ListActivity {

	public static final String TAG = "TAG";
	private static final int REQUEST_CODE = 123;
	private ListView list;
	private SimpleCursorAdapter listAdapter;
	private TodoDao dao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoginManager loginManager = Utils.getLoginManager(this);
		if (!loginManager.isLoggedIn()) {
			showLogin();
			return;
		}
		setContentView(R.layout.activity_todo_list);
		list = this.getListView();

		dao = new TodoDao(this);
		Cursor query = dao.query(loginManager.userId, true);
		String[] from = { TodoDao.C_CONTENT};
		int[] to = { R.id.todoCheckbox};
		listAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, query, from, to,
				SimpleCursorAdapter.FLAG_AUTO_REQUERY);
		ViewBinder binder = new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Cursor cursor, int colIdx) {
				if (view.getId() == R.id.todoCheckbox) {
					final CheckBox box = (CheckBox) view;
					box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							updateStrike(box);
						}
					});
					box.setChecked(!(cursor.getShort(cursor.getColumnIndex(TodoDao.C_DONE)) == 0));
					updateStrike(box);
					//return false -> niech binder wywo³a jest setText
				}
				return false;
			}
		};
		listAdapter.setViewBinder(binder);
		list.setAdapter(listAdapter);
	}

	private void updateStrike(CheckBox box) {
		box.setPaintFlags(box.isChecked() ? box.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : box.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
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
						Todo todo = Todo.fromJsonObject(object);
						dao.insertOrUpdate(todo);
						ret.add(todo);
					}
					return ret;
				} catch (Exception e) {
					e.printStackTrace();
					lastError = e;
					return null;
				}
			}

			protected void onPostExecute(java.util.List<Todo> result) {
			};
		}.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, token);
	}

	private void showLogin() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

}
