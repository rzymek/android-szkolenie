package org.rzymek.todoexpert;

import java.text.ParseException;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import pl.allegro.todo.dao.Todo;
import pl.allegro.todo.utils.HttpUtils;
import pl.allegro.todo.utils.Utils;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddActivity extends Activity {
	private static final String RESULT = "result";
	private static final int MIN_LENGTH = 4;
	private static final String TAG = "XXXX";
	private EditText itemText;
	private CheckBox doneBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_add);
		itemText = (EditText) findViewById(R.id.itemText);
		itemText.setText(getIntent().getStringExtra(TodoListActivity.TAG));
		doneBox = (CheckBox) findViewById(R.id.completedBox);
		Button add = (Button) findViewById(R.id.add_button);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				addToDo();
			}
		});
		if (savedInstanceState == null && intent != null) {
			Log.d(TAG, "intent != null");
			if (Intent.ACTION_SEND.equals(intent.getAction())) {
//				Bundle ex = intent.getExtras();
//				Set<String> keySet = ex.keySet();
//				String s = "";
//				for (String key : keySet) {
//					s += key + ":" + ex.get(key) + "\n";
//				}
				String message = intent.getStringExtra(Intent.EXTRA_TEXT);
				String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
				itemText.setText(subject+"\n"+message);
			}
		}
	}

	protected void addToDo() {
		String text = itemText.getText().toString();
		if (TextUtils.isEmpty(text) || text.length() < MIN_LENGTH) {
			itemText.setError(getString(R.string.invalid_text, MIN_LENGTH));
			return;
		}
		boolean isDone = doneBox.isChecked();
		Todo todo = new Todo();
		todo.content = text;
		todo.done = isDone;
		new AsyncTask<Todo, Void, JSONObject>() {
			private Exception lastError;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				setProgressBarVisibility(true);
				lastError = null;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);
				setProgressBarVisibility(false);
				if(lastError != null){
					Utils.toast(getApplicationContext(), lastError.toString());
					return;
				}					
				try {
					Intent intent = new Intent();
					Todo todo = Todo.fromJsonObject(result);
					intent.putExtra(RESULT, todo);
					setResult(RESULT_OK, intent);
					finish();
					TodoApp app = (TodoApp) getApplication();
					app.dao.insertOrUpdate(todo);
					sendBroadcast(new Intent(RefreshIntentService.REFRESH));
				} catch (JSONException | ParseException e) {
					Utils.toast(getApplicationContext(), e.toString());
				}
			}

			@Override
			protected JSONObject doInBackground(Todo... params) {
				try {
					TodoApp app = (TodoApp) getApplication();
					String json = params[0].toJsonStringForPost();
					return new JSONObject(HttpUtils.postTodo(json, app.getLoginManager().token));
				} catch (Exception ex) {
					lastError = ex;
					return null;
				}
			}
		}.execute(todo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			AccountManager service = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
			Account[] accounts = service.getAccounts();
			for (Account account : accounts) {
				Log.i("ACNT",account.type+":"+account.name);				
			}
			Log.i("--","--------------");
			AuthenticatorDescription[] authenticatorTypes = service.getAuthenticatorTypes();
			for (AuthenticatorDescription desc : authenticatorTypes) {
				Log.i("DESC",desc.toString());								
			}
			Account[] accountsByType = service.getAccountsByType("com.github");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
