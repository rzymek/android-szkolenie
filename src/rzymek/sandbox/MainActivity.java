package rzymek.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

public class MainActivity extends Activity {

	private AutoCompleteTextView auto;
	List<String> split = Arrays
			.asList("Object Relational Mapping Lite (ORM Lite) provides some lightweight functionality for persisting Java objects to SQL databases while avoiding the complexity and overhead of more standard ORM packages. It supports a number of SQL databases using JDBC and also supports Sqlite with native calls to Android OS database APIs. Documentation about how to configure ORMLite for Android specifically is available in the manual."
					.split(" "));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		auto = (AutoCompleteTextView) findViewById(R.id.auto);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
			@Override
			public Filter getFilter() {
				return new Filter() {

					@Override
					protected FilterResults performFiltering(CharSequence constraint) {
						FilterResults results = new FilterResults();
						ArrayList<String> res = new ArrayList<String>();
						int len = constraint.length();
						for (String string : split) {
							if(string.length() == len) 
								res.add(string);
						}
						results.values = res;
						results.count = res.size();
						return results;
					}

					@Override
					protected void publishResults(CharSequence constraint, FilterResults results) {
						if(results.count > 0) {
							clear();
							addAll((List<String>)results.values);
							notifyDataSetChanged();
						}else{
							notifyDataSetInvalidated();
						}
					}

				};
			}
		};
//		adapter.clear();
//		Log.w("XXX", Arrays.asList(split).toString());
//		adapter.addAll(Arrays.asList(split));
		auto.setAdapter(adapter);

	}
}
