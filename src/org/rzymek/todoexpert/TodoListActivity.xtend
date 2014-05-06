package org.rzymek.todoexpert

import android.app.Activity
import android.os.Bundle
import android.view.Menu

class TodoListActivity extends Activity {
	
	override protected onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState)	
	}
	
	override onCreateOptionsMenu(Menu menu) {
		menuInflater.inflate(R.menu.todo_list_menu, menu);
		true;
	}
	
}