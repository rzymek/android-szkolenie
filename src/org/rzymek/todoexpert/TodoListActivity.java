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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_menu, menu);
		return true;
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
				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
