package org.rzymek.todoexpert;

import pl.allegro.todo.dao.Todo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
		Intent intent = getIntent();
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
		if (savedInstanceState == null && intent != null) {
		    Log.d(TAG, "intent != null");
		    if (intent.getAction().equals(Intent.ACTION_SEND)) {
		        Log.d(TAG, "intent.getAction().equals(Intent.ACTION_SEND)");
		        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
		        String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
		        itemText.setText(subject+"\n"+message);
		    }
		}
	}

	protected void addToDo() {
		String text = itemText.getText().toString();
		if(TextUtils.isEmpty(text) || text.length() < MIN_LENGTH) {
			itemText.setError(getString(R.string.invalid_text, MIN_LENGTH));
			return;
		}
		boolean isDone = doneBox.isChecked();
		Intent intent = new Intent();
		Todo todo = new Todo();
		todo.content = text;
		todo.done = isDone;
		intent.putExtra(RESULT, todo);
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
