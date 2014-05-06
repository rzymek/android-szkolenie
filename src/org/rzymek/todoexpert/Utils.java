package org.rzymek.todoexpert;

import android.content.Context;
import android.widget.Toast;

public class Utils {
	public static void toast(Context ctx, String s) {
		Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
	}
} 