package rzymek.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	private AutoCompleteTextView auto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		auto = (AutoCompleteTextView) findViewById(R.id.auto);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
		adapter.clear();
		adapter.addAll("Object Relational Mapping Lite (ORM Lite) provides some lightweight functionality for persisting Java objects to SQL databases while avoiding the complexity and overhead of more standard ORM packages. It supports a number of SQL databases using JDBC and also supports Sqlite with native calls to Android OS database APIs. Documentation about how to configure ORMLite for Android specifically is available in the manual.".split(" *"));
		auto.setAdapter(adapter);
	}
}
