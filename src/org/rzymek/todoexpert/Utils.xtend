package org.rzymek.todoexpert

import android.content.Context
import android.widget.Toast

class Utils { 
	def static toast(Context ctx, String s) {
		Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
	}
}