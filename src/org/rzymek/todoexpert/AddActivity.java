package org.rzymek.todoexpert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddActivity extends Activity {
	private static final String RESULT = "result";
	private EditText itemText;
	private CheckBox doneBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		itemText = (EditText) findViewById(R.id.itemText);
		itemText.setText(getIntent().getStringExtra(TodoListActivity.TAG));
		doneBox= (CheckBox) findViewById(R.id.completedBox);
		Button add = (Button) findViewById(R.id.add_button);
		add.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				addToDo();
			}
		});
	}

	protected void addToDo() {
		String text = itemText.getText().toString();
		if(TextUtils.isEmpty(text) || text.length() < 3) {
			itemText.setError("Enter some text at least 3 characters long");
			return;
		}
		boolean isDone = doneBox.isChecked();
		Intent intent = new Intent();
		intent.putExtra(RESULT, new Todo(text, isDone));
		setResult(RESULT_OK, intent);
		finish();
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
