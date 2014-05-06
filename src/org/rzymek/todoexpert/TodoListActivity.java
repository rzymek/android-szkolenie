package org.rzymek.todoexpert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TodoListActivity extends Activity {

	public static final String TAG = "TAG";
	private static final int REQUEST_CODE = 123;
	private static final String LOG = "!!!!!";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
